package com.example.shoppinglist;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FirstFragment extends Fragment {

    private Snackbar outdatedNotice;

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView shoppingList = view.findViewById(R.id.shopping_list);
        ShoppingListViewModel model = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        ArrayAdapter<ShoppingItem> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_list_item_1) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView row = (TextView) super.getView(position, convertView, parent);
                ShoppingItem item = Objects.requireNonNull(getItem(position));
                int flags = row.getPaintFlags();
                row.setPaintFlags(item.purchased
                        ? flags | Paint.STRIKE_THRU_TEXT_FLAG
                        : flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
                ViewCompat.setStateDescription(row, getContext().getText(item.purchased
                        ? R.string.shopping_item_purchased : R.string.shopping_item_pending));
                return row;
            }
        };

        shoppingList.setAdapter(adapter);
        shoppingList.setOnItemClickListener((parent, row, position, id) -> {
            ShoppingItem item = Objects.requireNonNull(adapter.getItem(position));
            model.togglePurchased(item.id);
        });

        model.getItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
        });

        Drawable normalBackground = shoppingList.getBackground();
        int warningColor = MaterialColors.layer(shoppingList,
                com.google.android.material.R.attr.colorSurface,
                com.google.android.material.R.attr.colorError, 0.12f);

        model.getListOutdated().observe(getViewLifecycleOwner(), outdated -> {
            if (Boolean.TRUE.equals(outdated)) {
                shoppingList.setBackground(new ColorDrawable(warningColor));
                outdatedNotice = Snackbar.make(view, R.string.shopping_list_outdated, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab);
                outdatedNotice.show();
            } else {
                shoppingList.setBackground(normalBackground);
                dismissOutdatedNotice();
            }
        });

        model.getStorageError().observe(getViewLifecycleOwner(), failed -> {
            if (Boolean.TRUE.equals(failed)) {
                Snackbar.make(view, R.string.shopping_list_storage_error, Snackbar.LENGTH_LONG).show();
                model.clearStorageError();
            }
        });
    }

    @Override
    public void onDestroyView() {
        dismissOutdatedNotice();
        super.onDestroyView();
    }

    private void dismissOutdatedNotice() {
        if (outdatedNotice != null) {
            outdatedNotice.dismiss();
            outdatedNotice = null;
        }
    }
}
