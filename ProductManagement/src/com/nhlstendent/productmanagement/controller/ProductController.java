package com.nhlstendent.productmanagement.controller;

import com.nhlstendent.productmanagement.model.Product;
import com.nhlstendent.productmanagement.productManager.ProductManager;
import com.nhlstendent.productmanagement.util.JsonUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ProductController {
    private List<Map<String, Object>> products = new ArrayList<>();
    private List<Map<String, Object>> originalProducts = new ArrayList<>();
    private Set<String> productNames = new HashSet<>();

    public void loadProductsFromFile(String filePath) {
        List<Product> parsedProducts = JsonUtil.parseProduct(filePath);
        checkDuplicateNames(parsedProducts);
        this.products = convertToDTO(parsedProducts);
        this.originalProducts = new ArrayList<>(products);
    }

    private void checkDuplicateNames(List<Product> products) {
        Set<String> names = new HashSet<>();
        for (Product product : products) {
            if (names.contains(product.getName())) {
                throw new IllegalArgumentException("The product already exist: " + product.getName());
            }
            names.add(product.getName());
        }
        this.productNames.addAll(names);
    }


    // Linear Search
    public List<Map<String, Object>> linearSearch(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Map<String, Object> product : products) {
            String name = (String) product.get("name");
            if (name.toLowerCase().contains(query.toLowerCase())) {
                results.add(product);
            }
        }

        if (results.isEmpty()) {
            results.add(Collections.singletonMap("Sorry", "no products matching"));
        }
        return results;
    }

    public void sortProductsByPrice() {
        Collections.sort(products, (o1, o2) -> {
            Double price1 = (Double) o1.get("price");
            Double price2 = (Double) o2.get("price");
            return price2.compareTo(price1); // Descending
        });
    }

    public void resetProducts() {
        products = new ArrayList<>(originalProducts);
    }

    public List<Map<String, Object>> getProducts() {
        return products;
    }

    private List<Map<String, Object>> convertToDTO(List<Product> products) {
        return products.stream().map(p -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("name", p.getName());
            dto.put("price", p.getPrice());
            dto.put("rating", p.getRating());
            return dto;
        }).collect(Collectors.toList());
    }
}