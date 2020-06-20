package com.example.openwms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDisplayActivity extends AppCompatActivity  {

    private TextView trackingCompany;
    private TextView userID;
    private String orderID;
    private String trackingCompanyStr;
    private String userIDStr;
    private String itemName;
    private String itemQuantity;
    private String itemScanned;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersdisplay);

        /* Referenced from: https://stackoverflow.com/questions/28144657/android-error-attempt-to-invoke-virtual-method-void-android-app-actionbar-on */
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        orderID = bundle.getString(OrdersActivity.orderIDDisplay);
        setTitle(orderID);

        db.collection("orders")
                .document(orderID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                final ArrayList<OrderItems> orderItemList = new ArrayList<>();
                List<Map<String,String>> itemsGroup = new ArrayList<>();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    itemsGroup = (ArrayList) document.get("items");
                    trackingCompanyStr = document.getString("trackingCompany");
                    userIDStr = document.getString("userID");

                    trackingCompany = (TextView) findViewById(R.id.logistic);
                    userID = (TextView) findViewById(R.id.username);

                    trackingCompany.setText(trackingCompanyStr);
                    userID.setText(userIDStr);

                    /* referenced from https://stackoverflow.com/questions/27485794/java-iterate-over-arraylist-with-hashmap-in-it */

                    for (Map<String,String> items : itemsGroup) {
                        for (String key : items.keySet()) {
                            itemName = items.get("itemName");
                            itemQuantity = items.get("quantity");
                            itemScanned = items.get("scanned");
                        }

                        orderItemList.add(new OrderItems(itemName, itemQuantity, itemScanned));

                        Log.d("Fuck","Item Name =>" + itemName);
                        Log.d("Fuck","Item Quantity =>" + itemQuantity);
                        Log.d("Fuck","Item Scanned =>" + itemScanned);

                        ListView listView = findViewById(R.id.orderProducts);
                        OrdersDisplayAdapter ordersDisplayAdapter = new OrdersDisplayAdapter(OrdersDisplayActivity.this, R.layout.list_item, orderItemList);
                        listView.setAdapter(ordersDisplayAdapter);
                    }



                    Log.d("This Fucking Shit", "This Fucking Shit Items => " + itemsGroup);

                    //     ListView listView = findViewById(R.id.orderProducts);
                    //  OrdersDisplayAdapter ordersDisplayAdapter = new OrdersDisplayAdapter(OrdersDisplayActivity.this, R.layout.list_item_scanning, order);
                    //    listView.setAdapter(ordersDisplayAdapter);

                   /* listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent displayOrders = new Intent(getBaseContext(), OrdersDisplayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(orderIDDisplay, orderDetails.get(position).getOrderID());

                                    displayOrders.putExtras(bundle);
                                    startActivity(displayOrders);
                                    //Toast.makeText(getBaseContext(), "You clicked " + orderDetails.get(position).getOrderID(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ); */
                }
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
