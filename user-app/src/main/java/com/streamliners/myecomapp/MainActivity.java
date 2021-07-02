package com.streamliners.myecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.myecomapp.controllers.AdapterCallbacksListener;
import com.streamliners.myecomapp.controllers.ProductsAdapter;
import com.streamliners.myecomapp.databinding.ActivityMainBinding;
import com.streamliners.myecomapp.tmp.ProductsHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding b;

    List<Product> products;
    Context context = this;
    ProductsAdapter adapter;
    Cart cart;
    private SharedPreferences sharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        cart = new Cart();

        setTitle("Products");

        products = ProductsHelper.getProducts();

        // Handle Shared preferences
        sharedPrefs = getPreferences(MODE_PRIVATE);
        getDataFromSharedPreference();

        setUpRecyclerView();
    }

    /**
     * override on pause to save data in shared preference
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPrefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cart);
        editor.putString(Constants.CART, json);
        editor.commit();
    }

    /**
     * get data from shared preference
     */
    void getDataFromSharedPreference() {
        Gson gson = new Gson();
        String json = sharedPrefs.getString(Constants.CART, "");
        if(json.isEmpty()){
            return;
        }
        cart = gson.fromJson(json, Cart.class);
    }


    /**
     * Setting up recycler view
     */
    private void setUpRecyclerView() {

        adapter = new ProductsAdapter(this, products, cart, new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int itemPosition) {
                setUpCart();
            }
        });

    b.list.setLayoutManager( new LinearLayoutManager(this));
    b.list.setAdapter(adapter);
    }

    /**
     * show cart details in summary
     */
    private void setUpCart() {
        b.total.setText("₹"+cart.total);
        b.noOfItems.setText(""+cart.noOfItems+" items");
    }


}