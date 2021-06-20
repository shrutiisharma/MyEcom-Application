package com.streamliners.module;

import java.util.Scanner;

public class CartOperations {

    Scanner scanner = new Scanner(System.in);

//  //add product to cart
    public  void addToCart(Cart cart, ProductOperations productOperations) {

        //check for the availability of product
        if (productOperations.allProducts.isEmpty()) {
            System.out.print("\u001B[31m" + "\nSorry! No product available to add to cart." + "\u001B[0m");
            return;
        }

        while (true) {

            System.out.println("Choose the product number you want to add to cart:" +
                    "\n0: Go Back");

            //array of objects for the name of the available products
            Object[] productNames = productOperations.allProducts.keySet().toArray();

            //display name of all available products
            for (int i = 0; i < productOperations.allProducts.size(); i++)
                System.out.println((i + 1) + ": " + productNames[i]);

            System.out.print("Please enter your choice: ");
            int menuOption = scanner.nextInt();

            //if go back option chosen, break the loop
            if (menuOption == 0) break;

            //index of the product in the product map
            int index = menuOption - 1;

            //accessing the product through the key which is accessed from it's index value
            Product product = productOperations.allProducts.get(productNames[index]);

            //checking the product type
            if (product.type == ProductType.TYPE_WB) {

                //taking quantity for the product
                System.out.print("\nPlease enter the quantity of the product: ");
                float qty = scanner.nextFloat();

                //adding the product in the cart
                cart.add(product, qty);
            }

            else if (product.type == ProductType.TYPE_VB) {

                System.out.print("\nChoose Variant to add: ");

                //Print variants of product
                for (int i = 0; i < productOperations.allProducts.get(productNames[index]).variants.size(); i++)
                    System.out.print("\n" + i + ": " + productOperations.allProducts.get(productNames[index]).variants.get(i));


                System.out.print("\nPlease enter your choice: ");
                int choice = scanner.nextInt();

                cart.add(productOperations.allProducts.get(productNames[choice]),productOperations.allProducts.get(productNames[choice]).variants.get(choice));
            }

            System.out.println("DONE! Product added to cart successfully.");
        }
    }



//  //edit product from the cart
    public void editFromCart(Cart cart, ProductOperations productOperations){

        //check for the availability of product in cart
        if (cart.cartItems.isEmpty()) {
            System.out.print("\u001B[31m" + "\nSorry! No product available in cart to edit." + "\u001B[0m");
            return;
        }

        while (true) {

            System.out.println("Choose the item number you want to edit:" +
                    "\n0: Go Back");

            //array of objects for the name of the available products
            Object[] itemNames = cart.cartItems.keySet().toArray();

            //display name of all available products
            for (int i = 0; i < cart.cartItems.size(); i++)
                System.out.println((i + 1) + ": " + itemNames[i]);

            System.out.print("Please enter your choice: ");
            int menuOption = scanner.nextInt();

            //if go back option chosen, break the loop
            if (menuOption == 0) break;

            //index of the product in the product map
            int index = menuOption - 1;

            //accessing the product through the key which is accessed from it's index value
            CartItem tempCart = cart.cartItems.get(itemNames[index]);

            //checking the product type
            if (tempCart.type == ProductType.TYPE_WB)       editWBPCart(cart, productOperations.allProducts.get(itemNames[index]));

            else if (tempCart.type == ProductType.TYPE_VB) {

                //to get key to access product from allProducts
                String key = itemNames[index].toString();
                String[] str = key.split(" ");
                int n = 0;

                //find out which variant to edit
                for (int i = 0; i < productOperations.allProducts.get(str[0]).variants.size();i++){
                    if (productOperations.allProducts.get(str[0]).variants.get(i).name.equals(str[1])){
                        n=i;
                    }
                }

                editVBPCart(cart, productOperations.allProducts.get(str[0]).variants.get(n), productOperations.allProducts.get(str[0]));
            }
        }
    }


    //edit WBP Cart
    public void editWBPCart(Cart cart, Product product) {
        System.out.print("Enter new quantity: ");
        float newQty = scanner.nextFloat();

        //removing the product from the cart and adding new product with new quantity
        cart.remove(product);
        cart.add(product,newQty);

        System.out.println("\033[0;32m" + "Quantity Updated to " + newQty + " successfully!" + "\033[0m");
    }


    //edit VBP Cart
    public void editVBPCart(Cart cart, Variant variant, Product product) {

        int choice = 1;

        while (choice != 0) {
            System.out.println("0: Go Back\n1: Increment \n2: Decrement");
            choice = scanner.nextInt();

            //increment
            if (choice == 1) {
                cart.add(product, variant);
                System.out.println("\033[0;32m" + "Incremented to " + cart.cartItems.get(product.name + " " + variant.name).qty + "\033[0m");
            }

            //decrement
            else if (choice == 2) {
                if (cart.cartItems.get(product.name + " " + variant.name).qty == 1) {
                    System.out.println("\033[0;31m" + "Cannot Decrement! Only 1 Left." + "\033[0m");
                    continue;
                }
                cart.decrementVBP(product, variant);
                System.out.println("\033[0;32m" + "Decremented to " + cart.cartItems.get(product.name + " " + variant.name).qty + "\033[0m");
            }
        }
    }



//  //remove product from the cart
    public  void removeFromCart(Cart cart, ProductOperations productOperations) {

        //check for the availability of product
        if (productOperations.allProducts.isEmpty()) {
            System.out.print("\u001B[31m" + "\nSorry! Your cart is already empty." + "\u001B[0m");
            return;
        }

        while (true) {

            System.out.println("Choose the product number you want to remove from cart:" +
                    "\n0: Go Back");

            //array of objects for the name of the available products
            Object[] itemNames = cart.cartItems.keySet().toArray();

            //display name of all available products
            for (int i = 0; i < cart.cartItems.size(); i++)
                System.out.println((i + 1) + ": " + itemNames[i]);

            System.out.print("Please enter your choice: ");
            int menuOption = scanner.nextInt();

            //if go back option chosen, break the loop
            if (menuOption == 0) break;

            //index of the product in the product map
            int index = menuOption - 1;

            String[] str = itemNames[index].toString().split(" ");

            if (str.length == 1)    cart.remove(productOperations.allProducts.get(itemNames[index]));
            else    cart.remove(productOperations.allProducts.get(str[0]));

            System.out.println("\033[0;32m" + "Product Removed from Cart." + "\033[0m");
        }
    }



//  //delete entire cart
    public void deleteCart(Cart cart, ProductOperations productOperations){
        cart.cartItems.clear();
    }
}

