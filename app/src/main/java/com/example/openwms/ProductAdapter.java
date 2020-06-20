package com.example.openwms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.openwms.Products;
import com.example.openwms.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Products> {

    private int resource;
    private ArrayList<Products> products;
    private Context context;

    public ProductAdapter(Context context, int resource, ArrayList<Products> products) {
        super(context, resource, products);
        this.resource = resource;
        this.products = products;
        this.context = context;
    }

    public ProductAdapter(@NonNull Context context, int resource) {
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

            // ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            TextView productName = (TextView) v.findViewById(R.id.productName);
            TextView onHand = (TextView) v.findViewById(R.id.quantityOnHand);
            TextView warehouseQty = (TextView) v.findViewById(R.id.quantityWarehouse);

            //imageView.setImageResource(products.get(position).getPhoto());
            productName.setText(products.get(position).getProductName());
            onHand.setText("On Hand: " + products.get(position).getProductQuantity());
            warehouseQty.setText("Warehouse: 0");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}