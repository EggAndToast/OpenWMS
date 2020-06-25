package com.example.openwms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Products> productItems;


    public ProductRecyclerAdapter(Context context, ArrayList<Products> productItems) {
        inflater = LayoutInflater.from(context);
        this.productItems = productItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_of_products, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.skuTag.setText(productItems.get(position).getSkuTag());
        holder.onHand.setText(productItems.get(position).getOnHand());
        holder.shipping.setText(productItems.get(position).getShipping());
        holder.productImage.setImageResource(productItems.get(position).getProductImage());
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView skuTag, onHand, shipping;
        public ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            skuTag = (TextView) itemView.findViewById(R.id.skuTag);
            onHand = (TextView) itemView.findViewById(R.id.inStock);
            shipping = (TextView) itemView.findViewById(R.id.incomingStock);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
        }


    }
}


