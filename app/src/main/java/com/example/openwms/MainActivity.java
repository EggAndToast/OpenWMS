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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int count = 0;
    private TextView pending;
    private TextView processing;
    private TextView shipping;
    private TextView confirming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(myToolbar);

        setTitle("Dashboard");

        pending = (TextView)findViewById(R.id.pending_number);
        processing = (TextView)findViewById(R.id.processed_number);
    /*    shipping = (TextView)findViewById(R.id.shipped_number);
        confirming = (TextView)findViewById(R.id.confirming_number); */

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:894665555800:android:027e35c6435fa9f1d8af2d") // Required for Analytics.
                .setProjectId("openinventory-29c18") // Required for Firebase Installations.
                .setApiKey("AIzaSyDvTTqCpKaV8C6jgG4H1KW8LUWH4sEMT_A") // Required for Auth.
                .build();

       /* https://stackoverflow.com/questions/37652328/how-to-check-if-a-firebase-app-is-already-initialized-on-android */

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this, options, "OpenVMS");
        }

        /* Updated Pending Order */

        db.collection("orders")
                .whereEqualTo("status", "Pending")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            Log.d("TAG","Count =" + count );
                            pending.setText(String.valueOf(count));
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        /* Updated Processed Order */

        db.collection("orders")
                .whereEqualTo("status", "Processed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            Log.d("TAG","Count =" + count );
                            processing.setText(String.valueOf(count));
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }


 //https://stackoverflow.com/questions/32808996/android-add-two-toolbars-in-the-same-activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the toolbar menu
        getMenuInflater().inflate(R.menu.top_nav, menu);

        // Inflate and initialize the bottom menu
        ActionMenuView bottomBar = (ActionMenuView)findViewById(R.id.bottom_toolbar);
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
            case R.id.orders:
                Intent orders = new Intent(this,OrdersActivity.class);
                startActivity(orders);
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

    public void getReadings(View view) {
        Intent intent = new Intent(this,sensorActivity.class);
        startActivity(intent);
        finish();
    }



}