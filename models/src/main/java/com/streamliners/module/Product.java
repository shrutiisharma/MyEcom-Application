package com.streamliners.module;

import java.util.List;

public class Product {

    //common
    public String name, imageURL;
    public int type;

    //WBP
    float minQuantity, pricePerKg;

    //VBP
    public List<Variant> variants;

    //for WBP
    public Product(String name, String imageURL, float pricePerKg, float minQuantity) {
        type = ProductType.TYPE_WB;
        this.name = name;
        this.imageURL = imageURL;
        this.pricePerKg = pricePerKg;
        this.minQuantity = minQuantity;
    }

    //for VBP
    public Product(String name, String imageURL, List<Variant> variants) {
        type = ProductType.TYPE_VB;
        this.name = name;
        this.imageURL = imageURL;
        this.variants = variants;
    }

    @Override
    public String toString() {

        if (type == ProductType.TYPE_WB)
            return name + "     ₹ " + pricePerKg + "/kg";
        else
            return name + "     " + variants;

    }
}
