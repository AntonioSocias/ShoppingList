package com.example.shoppinglist;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class ShoppingItem {

    public final String id;
    public final String name;
    public final boolean purchased;

    public ShoppingItem(String id, String name, boolean purchased) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.purchased = purchased;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
