package com.streamliners.myecomapp.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.module.Cart;
import com.streamliners.module.Product;
import com.streamliners.myecomapp.databinding.DialogWeightPickerBinding;

import java.util.ArrayList;
import java.util.List;

public class WeightPickerDialog {
    private Context context;
    Cart cart;


    public WeightPickerDialog(Context context, Cart cart) {

        this.context = context;
        this.cart = cart;
    }

    /**
     * dialog show
     *
     * @param product
     * @param listener
     */
    public void show(Product product, WeightPickerCompleteListener listener) {
        DialogWeightPickerBinding binding = DialogWeightPickerBinding.inflate(LayoutInflater.from(context));
        binding.wbProductName.setText(product.name);

        float minQty = product.minQuantity;
        List<String> kgList = new ArrayList<>();
        for (int i = (int) minQty; i <= 10; i++) {
            kgList.add(i + "Kg");
        }
        //Setup kg picker
        //We can't store arrayList directly in number picker .we've to convert it into string array.
        String[] kgs = new String[kgList.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < kgList.size(); j++) {
            // Assign each value to String array
            kgs[j] = kgList.get(j);
        }
        //it shows the size of the list.
        binding.wbKgPicker.setMinValue(0);
        binding.wbKgPicker.setMaxValue(10 - (int) minQty);
        binding.wbKgPicker.setDisplayedValues(kgs);



        List<String> gList = new ArrayList<>();
        String number = String.valueOf(minQty);
        number = number.substring(number.indexOf(".")).replace(".", "");
        int n = Integer.parseInt(number);

        if (number.length() == 1)
            n *= 100;
        else
            n *= 10;

        for (int i = n; i <= 950; i += 50) {
            gList.add(i + "g");
        }
        //Setup gram picker
        String[] grams = new String[gList.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < gList.size(); j++) {
            // Assign each value to String array
            grams[j] = gList.get(j);
        }
        binding.wbGramPicker.setMinValue(0);
        //forEx- 500g
        //19-500/50=9, there will be 9+1 elements bcz it starts with zero.
        binding.wbGramPicker.setMaxValue(19 - n / 50);
        binding.wbGramPicker.setDisplayedValues(grams);

        //completeGram picker
        String[] completeGram = new String[20];
        for (int i = 0; i <= 950; i += 50)
            completeGram[i / 50] = i + "g";

        //reset picker
        if (cart.cartItems.containsKey(product.name)) {
            float qty = cart.cartItems.get(product.name).qty;
            int kg = (int) qty;
            binding.wbKgPicker.setValue(kg - (int) minQty);

            String gram = String.valueOf(qty);
            gram = gram.substring(gram.indexOf(".")).replace(".", "");
            int g = Integer.parseInt(gram);
            if (gram.length() == 1)
                g *= 100;
            else
                g *= 10;
            if (kg != (int) minQty) {
                binding.wbGramPicker.setDisplayedValues(completeGram);
                binding.wbGramPicker.setMaxValue(19);
                binding.wbGramPicker.setValue(g / 50);
            } else {
                binding.wbGramPicker.setValue(g / 50 - n / 50);
            }
        }


        int finalN = n;
        binding.wbKgPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal != 0) {
                    binding.wbGramPicker.setDisplayedValues(completeGram);
                    binding.wbGramPicker.setMaxValue(19);
                } else {
                    binding.wbGramPicker.setMaxValue(19 - finalN / 50);
                    binding.wbGramPicker.setDisplayedValues(grams);
                }
            }
        });

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

                String pickedGram;
                if (Integer.parseInt(pickedKg) != (int) minQty) {
                    pickedGram = completeGram[binding.wbGramPicker.getValue()].replace("g", "");
                } else
                    pickedGram = grams[binding.wbGramPicker.getValue()].replace("g", "");

                float qty = Float.parseFloat(pickedGram) / 1000 + Float.parseFloat(pickedKg);
                if (qty < product.minQuantity) {
                    Toast.makeText(context, "Select a quantity is greater than " + product.minQuantity, Toast.LENGTH_SHORT).show();
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
                if (cart.cartItems.containsKey(product.name)) {
                    cart.remove(product);
                } else {
                    Toast.makeText(context, "Cart is already empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                dialog.dismiss();
                listener.onCompleted();
            }
        });
    }

    public interface WeightPickerCompleteListener {
        void onCompleted();
    }

}
