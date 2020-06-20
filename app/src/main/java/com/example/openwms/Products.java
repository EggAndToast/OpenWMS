package com.example.openwms;

public class Products {

    private String productName;
    private String productQuantity;

    public Products(String productName, String productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

}
