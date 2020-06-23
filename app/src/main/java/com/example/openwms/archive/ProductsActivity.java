package com.example.openwms.archive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.openwms.MainActivity;
import com.example.openwms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Products> products = new ArrayList<>();
    final ArrayList<Products> productList = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(myToolbar);

        setTitle("Products");

        db.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        productList.add(new Products(
                                document.getId(),
                                document.getString("quantity")));
                     }
                    Log.w("TAG", "Hello Products?" + productList);

                    ListView listView = findViewById(R.id.productListing);
                    ProductAdapter productAdapter = new ProductAdapter(ProductsActivity.this, R.layout.list_item, productList);
                    listView.setAdapter(productAdapter);

                 } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
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
                Intent dashboard = new Intent(this, MainActivity.class);
                startActivity(dashboard);
                finish();
                return true;
            case R.id.orders:
                Intent orders = new Intent(this,OrdersActivity.class);
                startActivity(orders);
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