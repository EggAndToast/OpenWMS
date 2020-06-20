package com.example.openwms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class OrdersDisplayAdapter extends ArrayAdapter<OrderItems> {

    private int resource;
    private ArrayList<OrderItems> itemDetails;
    private Context context;

    public OrdersDisplayAdapter(Context context, int resource, ArrayList<OrderItems> itemDetails) {
        super(context, resource, itemDetails);
        this.resource = resource;
        this.itemDetails = itemDetails;
        this.context = context;
    }

    public OrdersDisplayAdapter(@NonNull Context context, int resource) {
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

            TextView productName = (TextView) v.findViewById(R.id.productName);
            TextView quantity = (TextView) v.findViewById(R.id.quantityNeeded);
            TextView scanned = (TextView) v.findViewById(R.id.quantityScanned);

            productName.setText(itemDetails.get(position).getItemName());
            quantity.setText("Qty: " + itemDetails.get(position).getItemQuantity());
            scanned.setText("Scanned: " + itemDetails.get(position).getItemScanned());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}