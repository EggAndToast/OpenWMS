package com.example.openwms.archive;

import java.util.List;

public class OrderItems {
    private String itemName;
    private String itemQuantity;
    private String itemScanned;

    public OrderItems(String itemName, String itemQuantity, String itemScanned) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemScanned = itemScanned;
    }

    public String getItemName() { return itemName; }

    public String getItemQuantity() { return itemQuantity; }

    public String  getItemScanned() { return itemScanned; }
}
