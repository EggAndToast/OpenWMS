package com.example.openwms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.Distribution;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private boolean isMainButtonOpen = false;
    private FloatingActionButton mainButton;
    private LinearLayout button1, button2;
    private RecyclerView productList;
    private RecyclerView.Adapter newProductAdapter;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Dashboard");

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

        getData();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getData();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_nav, menu);

        mainButton = findViewById(R.id.mainButton);

        button1 = (LinearLayout) findViewById(R.id.button1);
        button2 = (LinearLayout) findViewById(R.id.button2);

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

        mainButton.animate().rotationBy(180);

        button1.animate().translationY(-80);
        button2.animate().translationY(-100);
    }

    private void closeMainButtonMenu(){
        isMainButtonOpen=false;

        mainButton.animate().rotationBy(180);

        button1.animate().translationY(0);
        button2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isMainButtonOpen) {
                    button1.setVisibility(View.GONE);
                    button2.setVisibility(View.GONE);
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

    public void getData() {
        db.collection("product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Products> productItems = new ArrayList<>();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String imageName = document.getString("image_link");
                                int imageID = getResources().getIdentifier(imageName, "drawable", getPackageName());

                                productItems.add(new Products(document.getId(),
                                        document.get("onHand").toString(),
                                        document.get("shipping").toString(),
                                        imageID
                                ));

                            }

                            int columns;
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                columns = 1;
                            }
                            else {
                                columns = 2;
                            }

                            productList = findViewById(R.id.productLayout);

                            //Replace with getScreenSize() if running on actual device
                            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, columns, GridLayoutManager.HORIZONTAL, false);
                            productList.setLayoutManager(layoutManager);

                            newProductAdapter = new ProductRecyclerAdapter(MainActivity.this, productItems);
                            productList.setAdapter(newProductAdapter);
                        }
                    }
                });
    }

    public int getScreenSize() {
        DisplayMetrics displayMetrics = MainActivity.this.getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidth / 180 + 0.5);
        Log.d("Size","Size =>" + displayMetrics.widthPixels);
        return noOfColumns;
    }

}