package com.example.openwms.archive;

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

            String SLA = getDate(orderDetails.get(position).getSLA());
            String dateOrder = getDate(orderDetails.get(position).getOrderDate());

            orderDate.setText("Date: " + dateOrder);
            platformUsed.setText(orderDetails.get(position).getPlatform());
            theSLA.setText("SLA: " + SLA);
            orderID.setText("Order ID: " + orderDetails.get(position).getOrderID());
            price.setText("$" + orderDetails.get(position).getTotalPrice());
            status.setText(orderDetails.get(position).getStatus());

            TextView warning = (TextView) v.findViewById(R.id.warning);
            warning.setText("Warning: " + getWarning(SLA, dateOrder) + " Days to ship");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

    /*
    * With Reference from https://stackoverflow.com/questions/41139218/from-timestamp-to-date-android
    * */

    private String getDate(String time) {
        Date newTime = new Date(time);
        SimpleDateFormat shortTime = new SimpleDateFormat("dd/MM/YYYY");
        shortTime.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return shortTime.format(newTime).toString();
    }

    private long getWarning(String SLA, String orderDate) throws ParseException {
        SimpleDateFormat shortTime = new SimpleDateFormat("dd/MM/yyyy");
        Date dateSLA = shortTime.parse(SLA);
        Date dateOrder = shortTime.parse(orderDate);
        long warningTime = TimeUnit.DAYS.convert(dateSLA.getTime() - dateOrder.getTime(), TimeUnit.MILLISECONDS);
        return warningTime;
     }

}