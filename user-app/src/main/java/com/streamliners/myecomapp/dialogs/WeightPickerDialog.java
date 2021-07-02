package com.streamliners.myecomapp.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.myecomapp.databinding.DialogWeightPickerBinding;

public class WeightPickerDialog {
    private Context context;
    Cart cart;


    public WeightPickerDialog(Context context, Cart cart) {

        this.context = context;
        this.cart = cart;
    }

    /**
     * dialog show
     * @param product
     * @param listener
     */
    public void show(Product product, WeightPickerCompleteListener listener) {
        DialogWeightPickerBinding binding = DialogWeightPickerBinding.inflate(LayoutInflater.from(context));
        binding.wbProductName.setText(product.name);

        //Setup kg picker
        String[] kgs = new String[]{"0Kg", "1Kg", "2Kg", "3Kg", "4Kg", "5Kg", "6Kg", "7Kg", "8Kg", "9Kg", "10Kg"};
        binding.wbKgPicker.setMinValue(0);
        binding.wbKgPicker.setMaxValue(10);
        binding.wbKgPicker.setDisplayedValues(kgs);

        //Setup gram picker
        String[] grams = new String[]{"0g", "50g", "100g", "150g", "200g", "250g", "300g", "350g", "400g", "450g", "500g", "550g", "600g", "650g", "700g", "750g", "800g", "850g", "900g", "950g"};
        binding.wbGramPicker.setMinValue(0);
        binding.wbGramPicker.setMaxValue(19);
        binding.wbGramPicker.setDisplayedValues(grams);

        //Showing dialog box
        AlertDialog dialog = new MaterialAlertDialogBuilder(context)
                .setView(binding.getRoot())
                .show();


        //Handling save button
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pickedKg = kgs[binding.wbKgPicker.getValue()];
                pickedKg = pickedKg.replace("Kg", "");

                String pickedGram = grams[binding.wbGramPicker.getValue()].replace("g", "");

                float qty = Float.parseFloat(pickedGram) / 1000 + Float.parseFloat(pickedKg);
                if(qty<product.minQuantity){
                    Toast.makeText(context, "Select a quantity is greater than "+product.minQuantity, Toast.LENGTH_SHORT).show();
                    return;
                }
                cart.add(product, qty);

                dialog.dismiss();
                listener.onCompleted();
            }
        });

        //Handling remove btn
        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(product);
                dialog.dismiss();
                listener.onCompleted();
            }
        });
    }

    public interface WeightPickerCompleteListener {
        void onCompleted();
    }

}
