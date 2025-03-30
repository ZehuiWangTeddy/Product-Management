package com.nhlstendent.productmanagement.util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.Product;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class JsonUtil
{
    public static MyArrayList<Product> parseProduct(String filePath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            
            String jsonContent = content.toString();
            
            MyArrayList<Product> products = new MyArrayList<>();
            
            // Remove square brackets from JSON arrays
            jsonContent = jsonContent.substring(1, jsonContent.length() - 1);
            
            // Split each product object
            String[] productStrings = jsonContent.split("\\},\\s*\\{");
            
            for (String productString : productStrings) {
                // Cleaning product strings
                productString = productString.replaceAll("[{}]", "");
                
                // Split attributes
                String[] properties = productString.split(",");
                
                Product product = new Product();
                
                for (String property : properties) {
                    String[] parts = property.split(":");
                    String key = parts[0].trim().replaceAll("\"", "");
                    String value = parts[1].trim().replaceAll("\"", "");
                    
                    switch (key) {
                        case "name":
                            product.setName(value);
                            break;
                        case "price":
                            product.setPrice(Double.parseDouble(value));
                            break;
                        case "rating":
                            product.setRating(Double.parseDouble(value));
                            break;
                    }
                }
                
                products.add(product);
            }
            
            return products;
        } catch (Exception e) {
            e.printStackTrace(); // Prints a full stack trace
            return new MyArrayList<>(); // Returns an empty MyArrayList instead of null
        }
    }
}