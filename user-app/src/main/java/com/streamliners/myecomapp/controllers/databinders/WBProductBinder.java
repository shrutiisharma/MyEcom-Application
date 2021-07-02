package com.streamliners.myecomapp.controllers.databinders;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.myecomapp.controllers.AdapterCallbacksListener;
import com.streamliners.myecomapp.databinding.ItemWbProductBinding;
import com.streamliners.myecomapp.dialogs.WeightPickerDialog;

public class WBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    /**
     * bind weight Based Product data
     * @param b
     * @param product
     */
    public void bind(ItemWbProductBinding b, Product product) {

        //binding image,name,subtitle of product
        b.image.setImageURI(Uri.parse(product.imageURL));
        b.productName.setText(product.name);
        b.productSubtitle.setText("₹" + product.pricePerKg + " / Kg");

        //Checking if cart contains the product name
        if (cart.cartItems.containsKey(product.name)) {
            b.addBtn.setVisibility(View.INVISIBLE);
            b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            b.qty.setText("" + cart.cartItems.get(product.name).qty);
        }

        //Setup add Btn
        b.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing weight picker dialog box
                new WeightPickerDialog(context, cart)
                        .show(product, new WeightPickerDialog.WeightPickerCompleteListener() {
                            @Override
                            public void onCompleted() {
                                //CallBack to update binding
                                Float qty = cart.cartItems.get(product.name).qty;
                                if (qty != 0) {
                                    b.zeroQtyGroup.setVisibility(View.INVISIBLE);
                                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                                    b.qty.setText("" + qty);
                                } else {
                                    b.zeroQtyGroup.setVisibility(View.VISIBLE);
                                    b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
                                }
                                listener.onCartUpdated(0);
                            }

                        });

            }
        });

        //Setup editBtn
        b.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Showing Weight Picker Dialog box
                new WeightPickerDialog(context, cart)
                        .show(product, new WeightPickerDialog.WeightPickerCompleteListener() {
                            @Override
                            public void onCompleted() {
                                //CallBack to update binding
                                Float qty = 0.0f;
                                if(cart.cartItems.containsKey(product.name)){
                                    qty = cart.cartItems.get(product.name).qty;
                                }

                                if (qty != 0) {
                                    b.zeroQtyGroup.setVisibility(View.INVISIBLE);
                                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                                    b.qty.setText("" + qty);
                                } else {
                                    b.zeroQtyGroup.setVisibility(View.VISIBLE);
                                    b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
                                }
                                listener.onCartUpdated(0);
                            }

                        });

            }
        });

    }

}
