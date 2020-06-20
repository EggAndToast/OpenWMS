package com.example.openwms;

import java.util.List;

public class Orders {

    private String orderID;
    private String SLA;
    private String orderDate;
    private String platform;
    private String status;
    private String totalPrice;

    public Orders(String orderID, String SLA, String orderDate, String platform, String status,
                  String totalPrice )
    {
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

    public String getSLA() {
        return SLA;
    }

    public String getOrderDate() { return orderDate; }

    public String getPlatform() { return platform; }

    public String getStatus() { return status; }

    public String getTotalPrice() { return totalPrice; }

}
