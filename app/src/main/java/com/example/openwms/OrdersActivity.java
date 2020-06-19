package com.example.openwms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";
    private ListView listView;
    private ArrayList<String> orderID = new ArrayList<>();
    private ArrayList<Timestamp> SLA = new ArrayList<>();
    private ArrayList<Timestamp> orderDate = new ArrayList<>();
    private ArrayList<String> platform = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private ArrayList<String> totalPrice = new ArrayList<>();

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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                orderDetails.add(new Orders(document.getId(),
                                        document.getTimestamp("SLA"),
                                        document.getTimestamp("orderDate"),
                                        document.getString("platform"),
                                        document.getString("status"),
                                        document.getString("totalPrice")));
                            }
                            ListView listView = findViewById(R.id.ordersLayout);
                            OrdersAdapter ordersAdapter = new OrdersAdapter(OrdersActivity.this, R.layout.sixlistformat, orderDetails);
                            listView.setAdapter(ordersAdapter);

                            listView.setOnItemClickListener(

                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            Toast.makeText(getBaseContext(), "You clicked " + orderDetails.get(position).getOrderID(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            Log.d("During DBCollection", "Size = " + orderDetails.size());

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }

                });



/*
        listView.setAdapter(new OrdersAdapter(this, R.layout.sixlistformat, orderDetails));
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Toast.makeText(getBaseContext(), "You clicked " + orderDetails.get(position), Toast.LENGTH_SHORT).show();
                    }
                }
        ); */
    }

    private void generateOrders() {


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
