package com.example.openwms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class itemScannedActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String itemName, itemQuantity;
    String status, qr_code;
    ArrayList<scanIn> itemView = new ArrayList<>();
    ListView contentsOfInformation;
    List<Map<String,String>> itemsInformation;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanneditem);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        status = intent.getStringExtra("KEY_STATUS");
        qr_code = intent.getStringExtra("QR_CODE");
        setTitle("Scan " + status);
        getData();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:894665555800:android:027e35c6435fa9f1d8af2d") // Required for Analytics.
                .setProjectId("openinventory-29c18") // Required for Firebase Installations.
                .setApiKey("AIzaSyDvTTqCpKaV8C6jgG4H1KW8LUWH4sEMT_A") // Required for Auth.
                .build();

        /* https://stackoverflow.com/questions/37652328/how-to-check-if-a-firebase-app-is-already-initialized-on-android */

        if (FirebaseApp.getApps(this).isEmpty()) {
            settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();

            FirebaseApp.initializeApp(this, options, "OpenVMS");
            db.setFirestoreSettings(settings);
        }
    }

    public void getData() {
            db.collection("shipment")
                    .document(qr_code)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            final StringBuilder itemScanned = new StringBuilder();


                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                itemsInformation = (ArrayList) document.get("contents");


                                for (Map<String,String> items : itemsInformation) {

                                    for(String key : items.keySet()) {
                                        itemName = items.get("itemName");
                                        itemQuantity = items.get("quantity");
                                    }

                                    itemScanned.append(itemName + " => Quantity: " + itemQuantity).append(System.getProperty("line.separator"));
                                }

                                itemView.add(new scanIn(document.getId(),itemScanned.toString()));

                                contentsOfInformation = findViewById(R.id.contentsOfInformation);
                                ScanInAdapter scanInAdapter = new ScanInAdapter(itemScannedActivity.this, R.layout.list_item_scanning, itemView);
                                contentsOfInformation.setAdapter(scanInAdapter);
                            }
                        }
        });

}

    public void scannedConfirm(View view) {
        for (Map<String,String> items : itemsInformation) {

            for(String key : items.keySet()) {
                itemName = items.get("itemName");
                itemQuantity = items.get("quantity");
            }
            changeNumbers(itemName,itemQuantity);

        }
    }

    public void changeNumbers(String newItem, String quantityOld) {


        //Toast.makeText(getApplicationContext(), newItem, Toast.LENGTH_LONG).show();
                  
        db.collection("product")
                .document(newItem)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Toast.makeText(getApplicationContext(), "Document SnapShot is: " + document.getData(), Toast.LENGTH_LONG).show();

                    /*

                    Integer oldQuantity = document.getLong("onHand").intValue();
                    Integer oldShippingQuantity = document.getLong("shipping").intValue();
                    Integer newQuantity =  Integer.parseInt(itemQuantity) + oldQuantity;
                    Integer newShippingQuantity = oldShippingQuantity - Integer.parseInt(itemQuantity);

                    Toast.makeText(getApplicationContext(), "New => " + newQuantity + " Ship => " + newShippingQuantity, Toast.LENGTH_SHORT).show();

                    /*
                    db.collection("product")
                            .document(itemName)
                            .update({
                                 "onHand", newQuantity,
                                    "shipping", newShippingQuantity
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });*/
                }
            }
            });


    }

}

