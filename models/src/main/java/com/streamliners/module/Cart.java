package com.streamliners.module;

import java.util.HashMap;

public class Cart {

    public HashMap<String, CartItem> cartItems = new HashMap<>();
     public int total, noOfItems;


    //method overloading
    //to add and edit WBP
    public void add(Product product, float qty){

        //if cart item already exists in the cart, then edit
        if (cartItems.containsKey(product.name)){

            total -= cartItems.get(product.name).cost();                     //total = total - current price

            cartItems.get(product.name).qty = qty;
        }

        //if item does not exist in the cart, i.e add for the first time
        else {
            CartItem item = new CartItem(product.name, product.pricePerKg, qty, product.type);
            cartItems.put(product.name, item);

            noOfItems++;
        }

        //update cart summary
        total += product.pricePerKg * qty;
    }

    //to add and edit VBP
    public void add(Product product, Variant variant){

        //to specify the variant, we make the key as product's name + variant's name
        //eg Kiwi 500g
        //we do this by String concatenation
        String key = product.name + " " + variant.name;

        //if cart item already exists in the cart, then edit
        if (cartItems.containsKey(key)){
            cartItems.get(key).qty++;
        }

        //if item does not exist in the cart, i.e add for the first time
        else {
            CartItem item = new CartItem(product.name, variant.price, 1, product.type);
            cartItems.put(key, item);
        }

        //update cart summary
        noOfItems++;
        total += variant.price;
    }


    //to remove the complete product
    public void remove(Product product) {

        if (product.type == ProductType.TYPE_WB)
            removeWBP(product);
        else
            removeAllVariantsOfVBP(product);

    }


    //to remove WBP
    private void removeWBP(Product product) {
        //update cart summary
        //this is done before removing because if the product is already removed it won't be possible to do these 2 steps
        total -= cartItems.get(product.name).cost();
        noOfItems--;

        //the "minus button" will appear only if the item already exists (done in UI) so we need not check its existence here
        cartItems.remove(product.name);
    }


    //to remove all variants of VBP
    private void removeAllVariantsOfVBP(Product product) {

        for (Variant variant : product.variants) {
            String key = product.name + " " + variant.name;

            if (cartItems.containsKey(key)) {
                //update cart summary
                total -= cartItems.get(key).cost();
                noOfItems -= cartItems.get(key).qty;

                cartItems.remove(key);
            }
        }
    }


    //to decrement variant
    public void decrementVBP(Product product, Variant variant){

        String key = product.name + " " + variant.name;

        //update cart items qty
        cartItems.get(key).qty--;

        //update cart summary
        total -= variant.price;
        noOfItems --;

        //Remove if qty = 0;
        if (cartItems.get(key).qty == 0)
            cartItems.remove(key);
    }


    @Override
    public String toString() {

        return "\u001B[33m" + "My Cart: \n" + "\u001B[0m" +
                cartItems.values() +
                String.format("\nTotal = ₹ %.2f \nNo. of Items = %.2f\n", total, noOfItems);
    }
}
