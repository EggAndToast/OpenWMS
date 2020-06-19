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

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<Orders> {

    private int resource;
    private ArrayList<Orders> orderDetails;
    private Context context;

    public OrdersAdapter(Context context, int resource, ArrayList<Orders> orderDetails) {
        super(context, resource, orderDetails);
        this.resource = resource;
        this.orderDetails = orderDetails;
        this.context = context;
    }

    public OrdersAdapter(@NonNull Context context, int resource) {
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

            TextView orderDate = (TextView) v.findViewById(R.id.orderDate);
            TextView platformUsed = (TextView) v.findViewById(R.id.platformUsed);
            TextView theSLA = (TextView) v.findViewById(R.id.theSLA);
            TextView orderID = (TextView) v.findViewById(R.id.orderID);
            TextView price = (TextView) v.findViewById(R.id.price);
            TextView status = (TextView) v.findViewById(R.id.status);

            //orderDate.setText(orderDetails.get(position).getOrderDate());
            platformUsed.setText(orderDetails.get(position).getPlatform());
           // theSLA.setText(orderDetails.get(position).getSLA());
            orderID.setText(orderDetails.get(position).getOrderID());
            price.setText(orderDetails.get(position).getTotalPrice());
            status.setText(orderDetails.get(position).getStatus());


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}