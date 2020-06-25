package com.example.openwms;

public class Products {

    private String skuTag, onHand, shipping;
    private int productImage;

    public Products(String skuTag, String onHand, String shipping, int productImage) {
        this.skuTag = skuTag;
        this.onHand = onHand;
        this.shipping = shipping;
        this.productImage = productImage;
    }

    public String getSkuTag() { return skuTag; }

    public String getOnHand() { return onHand; }

    public String getShipping() { return shipping; }

    public int getProductImage() { return productImage; }
}
