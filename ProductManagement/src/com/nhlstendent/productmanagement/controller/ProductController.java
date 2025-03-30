package com.nhlstendent.productmanagement.controller;

import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.MyHashMap;
import com.nhlstendent.productmanagement.model.MyHashSet;
import com.nhlstendent.productmanagement.model.Product;
import com.nhlstendent.productmanagement.productManager.ProductManager;
import com.nhlstendent.productmanagement.util.JsonUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ProductController {
    private MyArrayList<MyHashMap<String, Object>> products = new MyArrayList<>();
    private MyArrayList<MyHashMap<String, Object>> originalProducts = new MyArrayList<>();
    private MyHashSet<String> productNames = new MyHashSet<>();

    public void loadProductsFromFile(String filePath) {
        MyArrayList<Product> parsedProducts = JsonUtil.parseProduct(filePath);
        checkDuplicateNames(parsedProducts);
        this.products = convertToDTO(parsedProducts);
        this.originalProducts = new ArrayList<>(products);
    }

    private void checkDuplicateNames(MyArrayList<Product> products) {
        MyHashSet<String> names = new MyHashSet<>();
        for (Product product : products) {
            if (names.contains(product.getName())) {
                throw new IllegalArgumentException("The product already exist: " + product.getName());
            }
            names.add(product.getName());
        }
        this.productNames.addAll(names);
    }


    // Linear Search
    public MyArrayList<MyHashMap<String, Object>> linearSearch(String query) {
        MyArrayList<MyHashMap<String, Object>> results = new MyArrayList<>();
        for (MyHashMap<String, Object> product : products) {
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

    // Binary Search by Price
    public MyArrayList<MyHashMap<String, Object>> binarySearchByPrice(double targetPrice) {
        MyArrayList<MyHashMap<String, Object>> sortedProducts = new MyArrayList<>(products);

        sortedProducts.sort(Comparator.comparingDouble(p -> (Double) p.get("price")));

        int left = 0, right = sortedProducts.size() - 1;
        MyArrayList<MyHashMap<String, Object>> result = new MyArrayList<>();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            double midPrice = (Double) sortedProducts.get(mid).get("price");

            if (midPrice == targetPrice) {
                int i = mid;
                while (i >= 0 && ((Double) sortedProducts.get(i).get("price")) == targetPrice) {
                    result.add(sortedProducts.get(i--));
                }
                i = mid + 1;
                while (i < sortedProducts.size() && ((Double) sortedProducts.get(i).get("price")) == targetPrice) {
                    result.add(sortedProducts.get(i++));
                }
                return result;
            } else if (midPrice < targetPrice) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return Collections.singletonList(Collections.singletonMap("Sorry", "No products found with price " + targetPrice));
    }


    public void sortProductsByPrice() {
        Collections.sort(products, (o1, o2) -> {
            Double price1 = (Double) o1.get("price");
            Double price2 = (Double) o2.get("price");
            return price2.compareTo(price1); // Descending
        });
    }

    public void sortProductsByName() {
        products = mergeSort(products);
    }

    private MyArrayList<MyHashMap<String, Object>> mergeSort(MyArrayList<MyHashMap<String, Object>> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        MyArrayList<MyHashMap<String, Object>> left = mergeSort(new MyArrayList<>(list.subList(0, mid)));
        MyArrayList<MyHashMap<String, Object>> right = mergeSort(new MyArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private MyArrayList<MyHashMap<String, Object>> merge(MyArrayList<MyHashMap<String, Object>> left, List<Map<String, Object>> right) {
        MyArrayList<MyHashMap<String, Object>> merged = new MyArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            String nameLeft = (String) left.get(i).get("name");
            String nameRight = (String) right.get(j).get("name");

            if (nameLeft.compareToIgnoreCase(nameRight) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }

    public void resetProducts() {
        products = new MyArrayList<>(originalProducts);
    }

    public MyArrayList<MyHashMap<String, Object>> getProducts() {
        return products;
    }

    private MyArrayList<MyHashMap<String, Object>> convertToDTO(MyArrayList<Product> products) {
        return products.stream().map(p -> {
            MyHashMap<String, Object> dto = new MyHashMap<>();
            dto.put("name", p.getName());
            dto.put("price", p.getPrice());
            dto.put("rating", p.getRating());
            return dto;
        }).collect(Collectors.toList());
    }
}