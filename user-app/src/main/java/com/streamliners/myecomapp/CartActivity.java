package com.streamliners.myecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.streamliners.module.Cart;
import com.streamliners.module.CartItem;
import com.streamliners.module.ProductType;
import com.streamliners.myecomapp.databinding.ActivityCartBinding;
import com.streamliners.myecomapp.databinding.CartItemBinding;
import com.streamliners.myecomapp.databinding.CartTotalBinding;
import java.util.Set;


public class CartActivity extends AppCompatActivity {


    private static final int ADDRESS_PICKER_REQUEST = 1020;


    ActivityCartBinding binding;
        Cart cart;
        Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context=this;
        getCart();
        openMap();

    }

    private void openMap() {
        binding.btnAddressPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocationPickerActivity.class);
                startActivityForResult(intent, ADDRESS_PICKER_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDRESS_PICKER_REQUEST) {
            try {
                if (data != null && data.getStringExtra("address") != null) {
                    String address = data.getStringExtra("address");
                    double currentLatitude = data.getDoubleExtra("lat", 0.0);
                    double currentLongitude = data.getDoubleExtra("long", 0.0);
                    binding.addressNotSet.setText("Address: "+address);


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getCart() {
    Intent intent = getIntent();
    cart = (Cart) intent.getSerializableExtra(Constants.CART);

    Set<String> keys = cart.cartItems.keySet();
    for(String key : keys){
        CartItem cartItem = cart.cartItems.get(key);
        CartItemBinding b = CartItemBinding.inflate(getLayoutInflater());
        b.productName.setText(key);
        if(cartItem.type== ProductType.TYPE_WB)
        b.costSummary.setText(cartItem.qty+"Kg"+" X "+"₹"+String.valueOf(cartItem.unitPrice).replaceFirst("\\.0+$", "")+"/Kg");
        else
            b.costSummary.setText((int)cartItem.qty+" X "+"₹"+String.valueOf(cartItem.unitPrice).replaceFirst("\\.0+$", ""));
        b.cost.setText("₹ "+String.valueOf(cartItem.cost()).replaceFirst("\\.0+$", ""));
        binding.cartList.addView(b.getRoot());
    }
        CartTotalBinding b = CartTotalBinding.inflate(getLayoutInflater());
        b.costSummary.setText(cart.noOfItems+" items");
        b.cost.setText("₹ "+cart.total);
        binding.cartList.addView(b.getRoot());

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.nameEt.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                    return;
                }else if(binding.contactNoEt.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please Enter a Contact No.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

}