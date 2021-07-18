package com.streamliners.myecomapp.controllers.databinders;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.module.Variant;
import com.streamliners.myecomapp.R;
import com.streamliners.myecomapp.controllers.AdapterCallbacksListener;
import com.streamliners.myecomapp.databinding.ChipVariantBinding;
import com.streamliners.myecomapp.databinding.ItemVbProductBinding;
import com.streamliners.myecomapp.dialogs.VariantsQtyPickerDialog;

import java.util.ArrayList;
import java.util.List;

public class VBProductBinder {

    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener) {
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    /**
     * bind data with variant based product
     * @param b
     * @param product
     */
    public void bind(ItemVbProductBinding b, Product product) {

        //binding name,subtitle and image
        b.image.setImageURI(Uri.parse(product.imageURL));
        b.productName.setText(product.name);
        b.productSubtitle.setText(product.variants.size()+" variants");
        //Show and hide variants
        b.dropDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b.variantChips.getVisibility()==View.GONE) {
                    b.variantChips.setVisibility(View.VISIBLE);
                    b.dropDownBtn.setImageResource(R.drawable.ic_arrow_drop_up);
                }
                else {
                    b.dropDownBtn.setImageResource(R.drawable.ic_arrow_drop_down);
                    b.variantChips.setVisibility(View.GONE);
                }
            }
        });

        b.variantChips.setClickable(false);


        int qty =0;

        //Adding chips
        for (Variant variant : product.variants) {
            ChipVariantBinding binding = ChipVariantBinding.inflate(LayoutInflater.from(context));
            binding.getRoot().setText("₹" +String.valueOf(variant.price).replaceFirst("\\.0+$", "") + " - " + variant.name);
            b.variantChips.addView(binding.getRoot());

            if(cart.cartItems.containsKey(product.name + " " + variant.name)){
                qty+=cart.cartItems.get(product.name + " " + variant.name).qty;
            }
        }
        if(qty!=0) {
            b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            b.qty.setText(""+qty);
        }

        //Setup increment btn
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when dialog is not needed
                if(product.variants.size()==1){
                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                    String qty = b.qty.getText().toString();
                    b.qty.setText("" + (Integer.parseInt(qty) + 1));
                    cart.add(product,product.variants.get(0));
                    listener.onCartUpdated(0);
                    return;
                }
                //Showing VariantsQtyPickerDialog
                new VariantsQtyPickerDialog(context, cart)
                        .show(product, new VariantsQtyPickerDialog.VariantsQtyPickerCompleteListener() {
                            @Override
                            public void onCompleted() {
                                int qty = 0;
                                for (Variant variant : product.variants) {
                                    if (cart.cartItems.containsKey(product.name + " " + variant.name))
                                        qty += cart.cartItems.get(product.name + " " + variant.name).qty;

                                }
                                if (qty != 0)
                                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                                else {
                                    b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
                                }
                                b.qty.setText(String.valueOf(qty).replaceFirst("\\.0+$", ""));
                                listener.onCartUpdated(0);
                            }
                        });
            }
        });

        //Setup decrement btn
        b.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decrement without showing dialog
                if(product.variants.size()==1){
                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                    String qty = b.qty.getText().toString();
                    b.qty.setText("" + (Integer.parseInt(qty) - 1));
                    cart.decrementVBP(product,product.variants.get(0));

                    if (qty.equals("1"))
                        b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
                    else {
                        b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                    }
                    listener.onCartUpdated(0);
                    return;
                }
                // Showing VariantsQtyPickerDialog
                new VariantsQtyPickerDialog(context, cart)
                        .show(product, new VariantsQtyPickerDialog.VariantsQtyPickerCompleteListener() {
                            @Override
                            public void onCompleted() {
                                int qty = 0;
                                for (Variant variant : product.variants) {
                                    if (cart.cartItems.containsKey(product.name + " " + variant.name))
                                        qty += cart.cartItems.get(product.name + " " + variant.name).qty;
                                }
                                if (qty != 0)
                                    b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
                                else {
                                    b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
                                }
                                b.qty.setText(String.valueOf(qty).replaceFirst("\\.0+$", ""));
                                listener.onCartUpdated(0);
                            }
                        });
            }
        });

    }


}




