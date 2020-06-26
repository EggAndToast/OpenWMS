package com.example.openwms;

import java.util.ArrayList;

public class scanIn {

    private String shipmentID;
    private String content;

    public scanIn(String shipmentID, String content) {
        this.shipmentID = shipmentID;
        this.content = content;
    }

    public String getScanContent() { return content; }

    public String getShipmentID() { return shipmentID; }


}
