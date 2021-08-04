package com.bfrisco.pistondrops;

import org.bukkit.Material;

public class NewDrop {
    private Material newDrop;
    private int quantity;

    public NewDrop(Material newDrop, int quantity) {
        this.newDrop = newDrop;
        this.quantity = quantity;
    }

    public Material getNewDrop() {
        return newDrop;
    }
    public int getQuantity() {
        return quantity;
    }

}
