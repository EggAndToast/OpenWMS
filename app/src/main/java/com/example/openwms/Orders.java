package com.example.openwms;

import android.util.Log;

import com.google.firebase.Timestamp;

public class Orders {

    private String orderID;
    private Timestamp SLA;
    private Timestamp orderDate;
    private String platform;
    private String status;
    private String totalPrice;

    public Orders(String orderID, Timestamp SLA, Timestamp orderDate, String platform, String status, String totalPrice) {
        this.orderID = orderID;
        this.SLA = SLA;
        this.orderDate = orderDate;
        this.platform = platform;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public Timestamp getSLA() {
        return SLA;
    }

    public Timestamp getOrderDate() { return orderDate; }

    public String getPlatform() { return platform; }

    public String getStatus() { return status; }

    public String getTotalPrice() { return totalPrice; }
}
