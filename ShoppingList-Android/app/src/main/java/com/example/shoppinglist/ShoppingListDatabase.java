package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

final class ShoppingListDatabase extends SQLiteOpenHelper {

    private final Context context;

    ShoppingListDatabase(Context context, String databaseName) {
        super(context.getApplicationContext(), databaseName, null, 1);
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE shopping_items ("
                + "id TEXT NOT NULL PRIMARY KEY, "
                + "name TEXT NOT NULL, "
                + "purchased INTEGER NOT NULL CHECK (purchased IN (0, 1)), "
                + "position INTEGER NOT NULL)");

        String[] samples = context.getResources().getStringArray(R.array.sample_shopping_items);
        for (int position = 0; position < samples.length; position++) {
            insertItem(db, new ShoppingItem("sample-" + position, samples[position], false), position);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new SQLiteException("Missing shopping list migration from " + oldVersion + " to " + newVersion);
    }

    List<ShoppingItem> readItems() {
        List<ShoppingItem> items = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT id, name, purchased FROM shopping_items ORDER BY position", null)) {
            while (cursor.moveToNext()) {
                items.add(new ShoppingItem(cursor.getString(0), cursor.getString(1), cursor.getInt(2) == 1));
            }
        }
        return items;
    }

    void togglePurchased(String id) {
        getWritableDatabase().execSQL(
                "UPDATE shopping_items SET purchased = 1 - purchased WHERE id = ?", new Object[]{id});
    }

    void replaceItems(List<ShoppingItem> snapshot) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("shopping_items", null, null);
            int position = 0;
            for (ShoppingItem item : snapshot) {
                insertItem(db, item, position++);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertItem(SQLiteDatabase db, ShoppingItem item, int position) {
        ContentValues values = new ContentValues();
        values.put("id", item.id);
        values.put("name", item.name);
        values.put("purchased", item.purchased ? 1 : 0);
        values.put("position", position);
        db.insertOrThrow("shopping_items", null, values);
    }
}
