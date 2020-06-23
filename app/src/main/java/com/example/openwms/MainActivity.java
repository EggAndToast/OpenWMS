package com.example.openwms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.openwms.archive.sensorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int count = 0;
    private boolean isMainButtonOpen = false;
    private TextView pending, processing, shipping, confirming;
    private FloatingActionButton mainButton, goToSensors, scanItems, addProducts, addShipment;
    private LinearLayout button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar myToolbar = (Toolbar) findViewById(R.id.page_toolbar);
       // setSupportActionBar(myToolbar);

        setTitle("Dashboard");

      /*     pending = (TextView)findViewById(R.id.pending_number);
        processing = (TextView)findViewById(R.id.processed_number);
     shipping = (TextView)findViewById(R.id.shipped_number);
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
                            //pending.setText(String.valueOf(count));
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
                           // processing.setText(String.valueOf(count));
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mainButton = findViewById(R.id.mainButton);

        goToSensors = (FloatingActionButton) findViewById(R.id.goToSensors);
        scanItems = (FloatingActionButton) findViewById(R.id.scanItems);
        addProducts = (FloatingActionButton) findViewById(R.id.addProducts);
        addShipment = (FloatingActionButton) findViewById(R.id.addShipment);

        button1 = (LinearLayout) findViewById(R.id.button1);
        button2 = (LinearLayout) findViewById(R.id.button2);
        button3 = (LinearLayout) findViewById(R.id.button3);
        button4 = (LinearLayout) findViewById(R.id.button4);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMainButtonOpen) {
                    openMainButtonMenu();
                }
                else {
                    closeMainButtonMenu();
                }


            }
        });

        return true;


    }

    private void openMainButtonMenu(){
        isMainButtonOpen=true;

        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);

        mainButton.animate().rotationBy(180);

        button1.animate().translationY(-20);
        button2.animate().translationY(-40);
        button3.animate().translationY(-60);
        button4.animate().translationY(-80);
    }

    private void closeMainButtonMenu(){
        isMainButtonOpen=false;

        mainButton.animate().rotationBy(180);

        button1.animate().translationY(0);
        button2.animate().translationY(0);
        button3.animate().translationY(0);
        button4.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isMainButtonOpen) {
                    button1.setVisibility(View.GONE);
                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.GONE);
                    button4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        }

    @Override
    public void onBackPressed() {
        if (isMainButtonOpen) {
            closeMainButtonMenu();
        } else {
            super.onBackPressed();
        }
    }



    public void getReadings(View view) {
        Intent intent = new Intent(this, sensorActivity.class);
        startActivity(intent);
    }

    public void getScanner(View view) {
        Intent intent = new Intent(this,scannerActivity.class);
        startActivity(intent);
    }


}