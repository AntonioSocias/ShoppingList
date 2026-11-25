package com.example.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ShoppingListDatabaseTest {

    private final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final String databaseName = "shopping_list_test_" + UUID.randomUUID() + ".db";

    @After
    public void deleteDatabase() {
        context.deleteDatabase(databaseName);
    }

    @Test
    public void importedItemsAndLocalTogglesSurviveReopening() {
        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            database.replaceItems(Arrays.asList(
                    new ShoppingItem("milk", "Leche", true),
                    new ShoppingItem("bread", "Pan", false)));
            database.togglePurchased("bread");
        }

        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            List<ShoppingItem> items = database.readItems();
            assertEquals(2, items.size());
            assertEquals("milk", items.get(0).id);
            assertEquals("Leche", items.get(0).name);
            assertTrue(items.get(0).purchased);
            assertEquals("bread", items.get(1).id);
            assertTrue(items.get(1).purchased);
            database.togglePurchased("bread");
        }

        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            assertFalse(database.readItems().get(1).purchased);
        }
    }

    @Test
    public void failedReplacementKeepsPreviousList() {
        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            database.replaceItems(Collections.singletonList(new ShoppingItem("milk", "Leche", true)));
            try {
                database.replaceItems(Arrays.asList(
                        new ShoppingItem("bread", "Pan", false),
                        new ShoppingItem("bread", "Otro pan", false)));
                fail("An import with duplicate IDs should fail");
            } catch (SQLiteConstraintException expected) {
                List<ShoppingItem> items = database.readItems();
                assertEquals(1, items.size());
                assertEquals("milk", items.get(0).id);
                assertTrue(items.get(0).purchased);
            }
        }
    }

    @Test
    public void anEmptyImportStaysEmptyAfterReopening() {
        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            assertFalse(database.readItems().isEmpty());
            database.replaceItems(Collections.emptyList());
        }

        try (ShoppingListDatabase database = new ShoppingListDatabase(context, databaseName)) {
            assertTrue(database.readItems().isEmpty());
        }
    }
}
