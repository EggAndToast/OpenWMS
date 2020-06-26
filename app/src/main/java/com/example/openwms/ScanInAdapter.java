package com.example.openwms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.openwms.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ScanInAdapter extends ArrayAdapter<scanIn> {

    private int resource;
    private ArrayList<scanIn> productDetails;
    private Context context;

    public ScanInAdapter(Context context, int resource, ArrayList<scanIn> productDetails) {
        super(context, resource, productDetails);
        this.resource = resource;
        this.productDetails = productDetails;
        this.context = context;
    }

    public ScanInAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            if (v == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(resource, parent, false);
            }

            TextView shipmentID = (TextView) v.findViewById(R.id.shipmentID);
            TextView contentArray = (TextView) v.findViewById(R.id.contentArray);

            shipmentID.setText(productDetails.get(position).getShipmentID());
            contentArray.setText(productDetails.get(position).getScanContent());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }



}