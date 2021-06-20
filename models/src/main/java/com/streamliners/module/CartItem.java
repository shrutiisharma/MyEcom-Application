package com.streamliners.module;

public class CartItem {

    String name;
    float unitPrice, qty;
    public int type;

    public CartItem(String name, float unitPrice, float qty, int type) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.type = type;
    }

    float cost(){
        return unitPrice * qty;
    }

    @Override
    public String toString() {
        return "\n" + name + String.format("     ( ₹ %.2f X %.2f = ₹ %.2f )", unitPrice, qty, cost());
    }
}
