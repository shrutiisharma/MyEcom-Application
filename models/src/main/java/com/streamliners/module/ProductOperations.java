package com.streamliners.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProductOperations {

//  //hashMap to store products
    public HashMap<String, Product> allProducts = new HashMap<>();

    Scanner scanner = new Scanner(System.in);


//  //to add products
    public void addProduct() {
        String menu = "Select Product Type: 0: Add Weight Based Product: 1: Add Variant Based Product: 2: Go Back Enter your choice:";


        int menuOption = 1;                     //given a value initially to start the loop

        //checking to go back from the menu
        while (menuOption != 2) {

            System.out.print(menu);
            menuOption = scanner.nextInt();

            //to add WBP
            if (menuOption == 0) addWBP(allProducts);

            //to add VBP
            else if (menuOption == 1) addVBP(allProducts);

            //invalid option
            else if (menuOption != 2)
                System.out.println("\u001B[31m" + "ERROR! INVALID Option. Please select from 0 to 2" + "\u001B[0m");
        }
    }

    //add WBP
    public void addWBP(HashMap<String, Product> allProducts) {

        //take the name of the product
        System.out.print("\nEnter the name of the product: ");

        //make sure the user enter something
        String name = scanner.nextLine();
        while (name.isEmpty()) name = scanner.nextLine();

        //key for the product in the allProducts map
        String key = name.toUpperCase();

        //check if the product already exists in the map
        if (allProducts.containsKey(key)) {
            System.out.println("\u001B[31m" + "ERROR! Product already exists." + "\u001B[0m");
            return;
        }

        //taking other necessary fields
        System.out.print("Enter image URL of the product: ");
        String imageURL = scanner.nextLine();

        System.out.print("Enter minimum quantity of the product: ");
        float minQty = scanner.nextFloat();

        System.out.print("Enter price per kg: ");
        float pricePerKg = scanner.nextFloat();

        allProducts.put(key, new Product(key, imageURL, pricePerKg, minQty));
        System.out.println("\u001B[32m" + "DONE! Product added successfully" + "\u001B[0m");
    }

    //add VBP
    public void addVBP(HashMap<String, Product> allProducts) {

        //take the name of the product
        System.out.print("\nEnter the name of the product: ");

        String name = scanner.nextLine();
        while (name.isEmpty()) name = scanner.nextLine();

        //key for the product in the allProducts map
        String key = name.toUpperCase();

        //check if the product already exists in the map
        if (allProducts.containsKey(key)) {
            System.out.println("\u001B[31m" + "ERROR! Product already exists." + "\u001B[0m");
            return;
        }

        //taking other necessary fields
        System.out.print("Enter image URL of the product: ");
        String imageURL = scanner.nextLine();

        System.out.print("Enter the variant of the product: ");                                                         //variant string
        String variantString = scanner.nextLine();

        //split the words entered for the variants
        String[] str = variantString.split(" ");

        //list of the variants added
        List<Variant> variantsList = new ArrayList<>();

        //separating the variants and adding them to the list
        for (int x = 0; x < str.length; x += 2) {
            Variant variant = new Variant(str[x], Float.parseFloat(str[x + 1]));
            variantsList.add(variant);
        }

        //adding the product in the products map
        allProducts.put(key, new Product(key, imageURL, variantsList));
        System.out.println("\u001B[32m" + "DONE! Product added successfully" + "\u001B[0m");
    }



//  //to edit products
    public void editProduct() {

        //check for the availability of product
        if (allProducts.isEmpty()) {
            System.out.print("\u001B[31m" + "\nSorry! No product available to edit." + "\u001B[0m");
            return;
        }

        int menuOption = 1;

        while (menuOption != 0) {
            //displaying menu to select product to edit
            System.out.print("Choose the product number you want to edit: 0: Go Back");


            //array of objects for the name of the available products
            Object[] productNames = allProducts.keySet().toArray();

            //display name of all available products
            for (int i = 0; i < allProducts.size(); i++) {
                System.out.print("\n" + (i + 1) + ": " + productNames[i]);
            }

            System.out.print("\nPlease enter your choice: ");
            menuOption = scanner.nextInt();

            //if go back option chosen, break the loop
            if (menuOption == 0) break;

            //index of the product in the product map
            int index = menuOption - 1;

            //accessing the product through the key which is accessed from it's index value
            Product product = allProducts.get(productNames[index]);

            //checking the product type
            if (product.type == ProductType.TYPE_WB) editWBP(product);

            else if (product.type == ProductType.TYPE_VB) editVBP(product);
        }
    }

    //to edit WBP
    public void editWBP(Product product) {

        System.out.println("You chose to edit the Weight Based Product.");

        String menu = " Select the field you want to edit: 0: Go Back 1: Edit name of the product. 2: Edit imageURL of the product. 3: Edit minimum quantity of the product. 4: Edit price per kg of the product. Enter your choice:";

        int menuOption = 1;

        while (menuOption != 0) {

            System.out.println(menu);
            menuOption = scanner.nextInt();

            //go back
            if (menuOption == 0) break;

            //edit name
            else if (menuOption == 1) {
                System.out.print("Enter new name of the product: ");
                String newName = scanner.nextLine();
                while (newName.isEmpty()) newName = scanner.nextLine();
                product.name = newName;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }

            //edit imageURL
            else if (menuOption == 2) {
                System.out.print("Enter new imageURL of the product: ");
                String newImageURL = scanner.nextLine();
                while (newImageURL.isEmpty()) newImageURL = scanner.nextLine();
                product.imageURL = newImageURL;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }

            //edit minimum quantity
            else if (menuOption == 3) {
                System.out.print("Enter new minimum quantity of the product: ");
                float newMinQuantity = scanner.nextFloat();
                product.minQuantity = newMinQuantity;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }

            //edit price per kg
            else if (menuOption == 4) {
                System.out.print("Enter new price per kg of the product: ");
                float newPricePerKg = scanner.nextFloat();
                product.pricePerKg = newPricePerKg;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }
        }
    }

    //to edit VBP
    public void editVBP(Product product) {

        System.out.println("You chose to edit the Variant Based Product.");

        String menu = " Select the field you want to edit: 0: Go Back 1: Edit name of the product. 2: Edit imageURL of the product. 3: Edit variant of the product. Enter your choice:";

        int menuOption = 1;

        while (menuOption != 0) {

            System.out.println(menu);
            menuOption = scanner.nextInt();

            //go back
            if (menuOption == 0) break;

                //edit name
            else if (menuOption == 1) {
                System.out.print("Enter new name of the product: ");
                String newName = scanner.nextLine();
                while (newName.isEmpty()) newName = scanner.nextLine();
                product.name = newName;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }

            //edit imageURL
            else if (menuOption == 2) {
                System.out.print("Enter new imageURL of the product: ");
                String newImageURL = scanner.nextLine();
                while (newImageURL.isEmpty()) newImageURL = scanner.nextLine();
                product.imageURL = newImageURL;
                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }

            //edit variant
            else if (menuOption == 3) {
                System.out.print("Enter the variant string of the product: ");
                String variantString = scanner.nextLine();
                while (variantString.isEmpty())     variantString = scanner.nextLine();

                //Separate the words entered for the variants
                String[] str = variantString.split(" ");

                //List of the variants added
                List<Variant> variants = new ArrayList<>();

                //Adding variants to the product
                for (int x = 0; x < str.length; x += 2) {
                    Variant variant = new Variant(str[x], Float.parseFloat(str[x + 1]));
                    variants.add(variant);
                }

                //Updating the product
                product.variants = variants;

                System.out.println("\u001B[32m" + "DONE! Edited successfully." + "\u001B[0m");
            }
        }
    }



//  //to remove or delete products
    public void deleteProduct() {

        //checking for the availability of product
        if (allProducts.isEmpty()) {
            System.out.print("\u001B[31m" + "\nSorry! No product available." + "\u001B[0m");
            return;
        }

        int menuOption = 1;

        while (menuOption != 0) {

            //displaying menu to select product to add in the cart
            System.out.print(" Choose the product number you want to delete: 0: Go Back");


            //array of objects for the name of the available products
            Object[] productNames = allProducts.keySet().toArray();

            //display name of all available products
            for (int i = 0; i < allProducts.size(); i++) {
                System.out.print("\n" + (i + 1) + ": " + productNames[i]);
            }

            System.out.print("\nPlease enter your choice: ");
            menuOption = scanner.nextInt();

            //if go back option chosen, break the loop
            if (menuOption == 0) break;

            //index of the product in the product map
            int index = menuOption - 1;

            //accessing the product through the key which is accessed from it's index value
            allProducts.remove(productNames[index]);

            System.out.println("\u001B[32m" + "DONE! Product deleted successfully." + "\u001B[0m");
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\u001B[33m").append("\nAll Products:\n").append("\u001B[0m");
        Object[] arr = allProducts.keySet().toArray();
        for (int i = 0; i < allProducts.size(); i++){
            sb.append(allProducts.get(arr[i].toString())).append("\n");
        }
        return sb.toString();
    }
}
