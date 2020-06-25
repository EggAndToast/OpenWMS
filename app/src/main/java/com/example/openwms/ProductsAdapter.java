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

public class ProductsAdapter extends ArrayAdapter<Products> {

    private int resource;
    private ArrayList<Products> productDetails;
    private Context context;

    public ProductsAdapter(Context context, int resource, ArrayList<Products> productDetails) {
        super(context, resource, productDetails);
        this.resource = resource;
        this.productDetails = productDetails;
        this.context = context;
    }

    public ProductsAdapter(@NonNull Context context, int resource) {
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

            TextView skuTag = (TextView) v.findViewById(R.id.skuTag);
            TextView onHand = (TextView) v.findViewById(R.id.inStock);
            TextView shipping = (TextView) v.findViewById(R.id.incomingStock);

            skuTag.setText(productDetails.get(position).getSkuTag());
            onHand.setText(productDetails.get(position).getOnHand());
            shipping.setText(productDetails.get(position).getShipping());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }



}