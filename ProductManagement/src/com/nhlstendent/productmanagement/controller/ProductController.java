package com.nhlstendent.productmanagement.controller;

import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.MyHashMap;
import com.nhlstendent.productmanagement.model.MyHashSet;
import com.nhlstendent.productmanagement.model.Product;
import com.nhlstendent.productmanagement.util.JsonUtil;

import java.util.Comparator;

public class ProductController
{
    private MyArrayList<MyHashMap<String, Object>> products = new MyArrayList<>();
    private MyArrayList<MyHashMap<String, Object>> originalProducts = new MyArrayList<>();
    private final MyHashSet<String> productNames = new MyHashSet<>();

    public void loadProductsFromFile(String filePath)
    {

        MyArrayList<Product> parsedProducts = JsonUtil.parseProduct(filePath);
        if (parsedProducts == null || parsedProducts.isEmpty())
        {
            throw new IllegalArgumentException("No products found in the file");
        }

        // check if the product name is already in the productNames set
        for (Product product : parsedProducts)
        {
            if (productNames.contains(product.getName()))
            {
                throw new IllegalArgumentException("The product already exist: " + product.getName());
            }
            productNames.add(product.getName());
        }

        this.products = convertToDTO(parsedProducts);

        // Create a deep copy of the original data
        this.originalProducts = new MyArrayList<>();
        for (MyHashMap<String, Object> product : products)
        {
            MyHashMap<String, Object> copy = new MyHashMap<>();
            copy.put("name", product.get("name"));
            copy.put("price", product.get("price"));
            copy.put("rating", product.get("rating"));
            originalProducts.add(copy);
        }
    }

    // Linear Search by name
    public MyArrayList<MyHashMap<String, Object>> linearSearch(String query)
    {
        MyArrayList<MyHashMap<String, Object>> results = new MyArrayList<>();
        for (MyHashMap<String, Object> product : products)
        {
            String name = (String) product.get("name");
            if (name.toLowerCase().contains(query.toLowerCase()))
            {
                results.add(product);
            }
        }

        if (results.isEmpty())
        {
            MyHashMap<String, Object> error = new MyHashMap<>();
            error.put("Sorry", "no products matching" + query);
            results.add(error);
        }
        return results;
    }

    // Binary Search by Price
    public MyArrayList<MyHashMap<String, Object>> binarySearchByPrice(double targetPrice)
    {
        MyArrayList<MyHashMap<String, Object>> sortedProducts = new MyArrayList<>();
        for (MyHashMap<String, Object> product : products)
        {
            sortedProducts.add(product);
        }

        sortedProducts.sort((o1, o2) ->
        {
            Double price1 = (Double) o1.get("price");
            Double price2 = (Double) o2.get("price");
            return price1.compareTo(price2);
        });

        int left = 0, right = sortedProducts.size() - 1;
        MyArrayList<MyHashMap<String, Object>> result = new MyArrayList<>();

        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            double midPrice = (Double) sortedProducts.get(mid).get("price");

            if (midPrice == targetPrice)
            {
                int i = mid;
                while (i >= 0 && ((Double) sortedProducts.get(i).get("price")) == targetPrice)
                {
                    result.add(sortedProducts.get(i--));
                }
                i = mid + 1;
                while (i < sortedProducts.size() && ((Double) sortedProducts.get(i).get("price")) == targetPrice)
                {
                    result.add(sortedProducts.get(i++));
                }
                return result;
            }
            else if (midPrice < targetPrice)
            {
                left = mid + 1;
            }
            else
            {
                right = mid - 1;
            }
        }

        MyHashMap<String, Object> error = new MyHashMap<>();
        error.put("Sorry", "No products found with price " + targetPrice);
        result.add(error);
        return result;
    }

    // Quick sort by price
    public void sortProductsByPrice() {
        Comparator<MyHashMap<String, Object>> priceComparator = (map1, map2) -> {
            Double price1 = (Double) map1.get("price");
            Double price2 = (Double) map2.get("price");
            return price2.compareTo(price1);
        };

        products = quickSort(products, 0, products.size() - 1, priceComparator);
    }

    private MyArrayList<MyHashMap<String, Object>> quickSort(
            MyArrayList<MyHashMap<String, Object>> list,
            int low,
            int high,
            Comparator<MyHashMap<String, Object>> comparator) {

        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
        return list;
    }

    private int partition(
            MyArrayList<MyHashMap<String, Object>> list,
            int low,
            int high,
            Comparator<MyHashMap<String, Object>> comparator) {

        MyHashMap<String, Object> pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                MyHashMap<String, Object> temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        MyHashMap<String, Object> temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    // Merge sort by name
    public void sortProductsByName()
    {
        products = mergeSort(products);
    }

    private MyArrayList<MyHashMap<String, Object>> mergeSort(MyArrayList<MyHashMap<String, Object>> list)
    {
        if (list.size() <= 1)
        {
            return list;
        }

        int mid = list.size() / 2;
        MyArrayList<MyHashMap<String, Object>> left = new MyArrayList<>();
        MyArrayList<MyHashMap<String, Object>> right = new MyArrayList<>();

        for (int i = 0; i < mid; i++)
        {
            left.add(list.get(i));
        }
        for (int i = mid; i < list.size(); i++)
        {
            right.add(list.get(i));
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private MyArrayList<MyHashMap<String, Object>> merge(MyArrayList<MyHashMap<String, Object>> left, MyArrayList<MyHashMap<String, Object>> right)
    {
        MyArrayList<MyHashMap<String, Object>> merged = new MyArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size())
        {
            String nameLeft = (String) left.get(i).get("name");
            String nameRight = (String) right.get(j).get("name");

            if (nameLeft.compareToIgnoreCase(nameRight) <= 0)
            {
                merged.add(left.get(i++));
            }
            else
            {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size())
        {
            merged.add(left.get(i++));
        }
        while (j < right.size())
        {
            merged.add(right.get(j++));
        }

        return merged;
    }

    public void resetProducts()
    {
        products = new MyArrayList<>();
        for (MyHashMap<String, Object> product : originalProducts)
        {
            MyHashMap<String, Object> copy = new MyHashMap<>();
            copy.put("name", product.get("name"));
            copy.put("price", product.get("price"));
            copy.put("rating", product.get("rating"));
            products.add(copy);
        }
    }

    public MyArrayList<MyHashMap<String, Object>> getProducts()
    {
        return products;
    }

    private MyArrayList<MyHashMap<String, Object>> convertToDTO(MyArrayList<Product> products)
    {
        MyArrayList<MyHashMap<String, Object>> result = new MyArrayList<>();
        for (Product product : products)
        {
            MyHashMap<String, Object> dto = new MyHashMap<>();
            dto.put("name", product.getName());
            dto.put("price", product.getPrice());
            dto.put("rating", product.getRating());
            result.add(dto);
        }
        return result;
    }
}