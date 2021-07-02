package com.streamliners.myecomapp.tmp;

import android.net.Uri;

import com.streamliners.module.Product;
import com.streamliners.module.Variant;
import com.streamliners.myecomapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsHelper {


    public static List<Product> getProducts(){

        List<Product> products = new ArrayList<>();
        Product apple = new Product("Apple",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.apple).toString(),100,2.5f);
        Product orange = new Product("Orange",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.orange).toString(),80,5);
        Product watermelon = new Product("Watermelon",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.watermelon).toString(),100,2);
        Product guava = new Product("Guava",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.guava).toString(),70,2);
        Product banana = new Product("Banana",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.banana).toString(),30,2);

        Product kiwi = new Product("Kiwi",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.kiwi).toString(),new ArrayList<>
                (Arrays.asList(new Variant("500g",96), new Variant("1Kg",180))
                ));
        Product sugar = new Product("Sugar",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.sugar).toString(),new ArrayList<>
                (Arrays.asList(new Variant("1Kg",100), new Variant("2Kg",200))
                ));
        Product strawberry = new Product("Strawberry",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.strawberry).toString(),new ArrayList<>
                (Arrays.asList(new Variant("1Kg",50))
                ));
        Product cherry = new Product("Cherry",Uri.parse("android.resource://com.streamliners.myecomapp/" + R.drawable.cheery).toString(),new ArrayList<>
                (Arrays.asList(new Variant("1Kg",60), new Variant("2Kg",120))
                ));


        products.add(apple);
        products.add(orange);
        products.add(watermelon);
        products.add(guava);
        products.add(banana);
        products.add(kiwi);
        products.add(sugar);
        products.add(strawberry);
        products.add(cherry);


        return products;
    }

}
