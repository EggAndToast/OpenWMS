package com.example.openwms;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addProduct extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String materialSelected;
    private Spinner spinnerMaterial;
    private EditText supplierName, skuName, colourName;
    private Map<String, String> inputData;
    private String documentPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        setTitle("Add New Product");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void saveProduct(View view) {

        spinnerMaterial = (Spinner) findViewById(R.id.spinnerMaterial);
        materialSelected = spinnerMaterial.getSelectedItem().toString();
        supplierName = (EditText) findViewById(R.id.supplierName);
        skuName = (EditText) findViewById(R.id.skuName);
        colourName = (EditText) findViewById(R.id.colourName);

        inputData = new HashMap<>();
        inputData.put("supplierName", supplierName.getText().toString());
        inputData.put("skuName", skuName.getText().toString());
        inputData.put("colourName", colourName.getText().toString());

        String documentPath = skuName.getText().toString() + "-" + materialSelected + "-1.75-" + colourName.getText().toString();
        Log.d("Doc Path", documentPath);

        db.collection("product").document(documentPath).set(inputData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "DocumentSnapshot successfully written!");
                Toast.makeText(addProduct.this, "Successfully Added New Product", Toast.LENGTH_SHORT).show();
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                        Toast.makeText(addProduct.this, "Failed to New Product", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });;

/*
        DocumentReference docRef =  db.collection("supplier").document(supplierName.getText().toString());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        goToAdd();
                    } else {
                        db.collection("supplier").document(supplierName.getText().toString()).set("Nothing").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(addProduct.this, "Successfully Added New Supplier", Toast.LENGTH_SHORT).show();
                                goToAdd();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error writing document", e);
                                        Toast.makeText(addProduct.this, "Failed to New Supplier", Toast.LENGTH_SHORT).show();
                                    }
                                });;
                    }
                } else {
                    Toast.makeText(addProduct.this, "Error has occurred. Please try again Later.", Toast.LENGTH_SHORT).show();
                }
            }
        }); */


    }

}
