package com.example.openwms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";
    private ListView listView;
    private ArrayList<String> orderID = new ArrayList<>();
    private ArrayList<Timestamp> SLA = new ArrayList<>();
    private ArrayList<Timestamp> orderDate = new ArrayList<>();
    private ArrayList<String> platform = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private ArrayList<String> totalPrice = new ArrayList<>();
    public static String orderIDDisplay;
    public static String orderItems;
    public static String orderTracking;
    public static String userID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(myToolbar);

        setTitle("Orders");

        db.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       final ArrayList<Orders> orderDetails = new ArrayList<>();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());



                                orderDetails.add(new Orders(document.getId(),
                                        document.getTimestamp("SLA").toDate().toString(),
                                        document.getTimestamp("orderDate").toDate().toString(),
                                        document.getString("platform"),
                                        document.getString("status"),
                                        document.getString("totalPrice")
                                        //, itemsGroup ,
                                      //  document.getString("trackingCompany"),
                                      //  document.getString("userID")

                                ));

                            }

                            ListView listView = findViewById(R.id.ordersLayout);
                            OrdersAdapter ordersAdapter = new OrdersAdapter(OrdersActivity.this, R.layout.sixlistformat, orderDetails);
                            listView.setAdapter(ordersAdapter);

                            listView.setOnItemClickListener(
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
                            );

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }

                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the toolbar menu
        getMenuInflater().inflate(R.menu.top_nav, menu);

        // Inflate and initialize the bottom menu
        ActionMenuView bottomBar = (ActionMenuView) findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.btm_nav, bottomMenu);
        for (int i = 0; i < bottomMenu.size(); i++) {
            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                Intent dashboard = new Intent(this,MainActivity.class);
                startActivity(dashboard);
                finish();
                return true;
            case R.id.products:
                Intent products = new Intent(this,ProductsActivity.class);
                startActivity(products);
                finish();
                return true;
            case R.id.accounting:
                Intent accounting = new Intent(this,AccountingActivity.class);
                startActivity(accounting);
                finish();
                return true;
            default:
                return true;
        }
    }


}
