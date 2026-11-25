package com.example.shoppinglist;

import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ShoppingListViewModel extends AndroidViewModel {

    private final ShoppingListDatabase database;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<List<ShoppingItem>> items = new MutableLiveData<>();
    private final MutableLiveData<Boolean> storageError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> listOutdated = new MutableLiveData<>(false);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ConnectivityManager connectivityManager;
    private boolean internetUnavailable;
    private boolean syncFailed;

    private final ConnectivityManager.NetworkCallback networkCallback =
            new ConnectivityManager.NetworkCallback() {
                @Override
                public void onCapabilitiesChanged(@NonNull Network network,
                        @NonNull NetworkCapabilities capabilities) {
                    boolean available = hasValidatedInternet(capabilities);
                    mainHandler.post(() -> setInternetUnavailable(!available));
                }

                @Override
                public void onLost(@NonNull Network network) {
                    mainHandler.post(() -> setInternetUnavailable(true));
                }
            };

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        database = new ShoppingListDatabase(application, "shopping_list.db");
        connectivityManager = application.getSystemService(ConnectivityManager.class);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        setInternetUnavailable(!hasValidatedInternet(capabilities));
        updateItems(() -> {});
    }

    private static boolean hasValidatedInternet(NetworkCapabilities capabilities) {
        return capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    private void setInternetUnavailable(boolean unavailable) {
        internetUnavailable = unavailable;
        updateListOutdated();
    }

    private void setSyncFailed(boolean failed) {
        syncFailed = failed;
        updateListOutdated();
    }

    private void updateListOutdated() {
        boolean outdated = internetUnavailable || syncFailed;
        if (!Boolean.valueOf(outdated).equals(listOutdated.getValue())) {
            listOutdated.setValue(outdated);
        }
    }

    LiveData<Boolean> getListOutdated() {
        return listOutdated;
    }

    /** Records a failed server request. A successfully stored snapshot clears this state. */
    @MainThread
    public void reportSyncFailure() {
        executor.execute(() -> mainHandler.post(() -> setSyncFailed(true)));
    }

    LiveData<List<ShoppingItem>> getItems() {
        return items;
    }

    LiveData<Boolean> getStorageError() {
        return storageError;
    }

    void clearStorageError() {
        storageError.setValue(false);
    }

    void togglePurchased(String id) {
        updateItems(() -> database.togglePurchased(id));
    }

    /**
     * Applies a complete snapshot after a successful download. The snapshot supplies the stable
     * IDs, ordering, and purchased states. An empty snapshot clears the saved list.
     */
    @MainThread
    public void importSnapshot(@NonNull List<ShoppingItem> snapshot) {
        List<ShoppingItem> copy = new ArrayList<>(snapshot.size());
        for (ShoppingItem item : snapshot) {
            copy.add(Objects.requireNonNull(item));
        }
        updateItems(() -> {
            database.replaceItems(copy);
            mainHandler.post(() -> setSyncFailed(false));
        });
    }

    private void updateItems(Runnable update) {
        executor.execute(() -> {
            try {
                update.run();
                items.postValue(database.readItems());
            } catch (SQLiteException e) {
                Log.e("ShoppingList", "Could not access the saved shopping list", e);
                storageError.postValue(true);
            }
        });
    }

    @Override
    protected void onCleared() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
        executor.execute(database::close);
        executor.shutdown();
    }
}
