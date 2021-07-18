package com.streamliners.myecomapp.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.module.Variant;
import com.streamliners.myecomapp.databinding.DialogVariantsQtyPickerBinding;
import com.streamliners.myecomapp.databinding.ItemVariantBinding;

public class VariantsQtyPickerDialog {

    private Context context;
    Cart cart;


    public VariantsQtyPickerDialog(Context context, Cart cart) {

        this.context = context;
        this.cart = cart;
    }

    /**
     * Show dialog
     * @param product
     * @param listener
     */
    public void show(Product product, VariantsQtyPickerCompleteListener listener) {
        DialogVariantsQtyPickerBinding binding = DialogVariantsQtyPickerBinding.inflate(LayoutInflater.from(context));
        binding.vbProductName.setText(product.name);

        //Inflate and setup item variants
        for (Variant variant : product.variants) {
            ItemVariantBinding b = ItemVariantBinding.inflate(LayoutInflater.from(context));
            if(cart.cartItems.containsKey(product.name + " " + variant.name)){
                b.nonZeroQtyGrp.setVisibility(View.VISIBLE);
                b.currentQty.setText(String.valueOf(cart.cartItems.get(product.name + " " + variant.name).qty).replaceFirst("\\.0+$", ""));
            }
            b.chipVariant.setText("₹" +String.valueOf(variant.price).replaceFirst("\\.0+$", "") + " - " + variant.name);

            //Handling increment btn
            b.btnInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.nonZeroQtyGrp.setVisibility(View.VISIBLE);
                    String qty = b.currentQty.getText().toString();
                    b.currentQty.setText("" + (Integer.parseInt(qty) + 1));
                    cart.add(product, variant);
                }
            });

            //Handling decrement btn
            b.btnDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String qty = b.currentQty.getText().toString();
                   qty =  String.valueOf(qty).replaceFirst("\\.0+$", "");
                    b.currentQty.setText("" + (Integer.parseInt(qty) - 1));

                    if (qty.equals("1")) {
                        b.nonZeroQtyGrp.setVisibility(View.INVISIBLE);
                    }
                    cart.decrementVBP(product, variant);
                }
            });

            binding.grpChipVariant.addView(b.getRoot());
        }

        //Showing dialog box
        AlertDialog dialog = new MaterialAlertDialogBuilder(context)
                .setView(binding.getRoot())
                .show();

        //Handling save button
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onCompleted();
            }
        });

        //Handling remove button
        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(product);
                dialog.dismiss();
                listener.onCompleted();
            }
        });

    }

    public interface VariantsQtyPickerCompleteListener {
        void onCompleted();
    }

}
