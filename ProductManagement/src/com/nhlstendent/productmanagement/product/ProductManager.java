package com.nhlstendent.productmanagement.product;

import java.io.File;
import java.util.*;

public class ProductManager
{
    private List<Product> arrayList;
    private List<Product> linkedList;
    private Set<String> productNames;
    private Map<String, Product> hashMap;

    public ProductManager()
    {
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
        this.productNames = new HashSet<>();
        this.hashMap = new HashMap<>();
    }

    public List<Product> getArrayList()
    {
        return arrayList;
    }

    public void setArrayList(List<Product> arrayList)
    {
        this.arrayList = arrayList;
    }

    public List<Product> getLinkedList()
    {
        return linkedList;
    }

    public void setLinkedList(List<Product> linkedList)
    {
        this.linkedList = linkedList;
    }

    public Set<String> getProductNames()
    {
        return productNames;
    }

    public void setProductNames(Set<String> productNames)
    {
        this.productNames = productNames;
    }

    public Map<String, Product> getHashMap()
    {
        return hashMap;
    }

    public void setHashMap(Map<String, Product> hashMap)
    {
        this.hashMap = hashMap;
    }

    public void importFromFile(String filePath)
    {
        List<Product> products = JsonUtils.parseProduct(filePath);
        if (products == null) {
            throw new RuntimeException("Failed to load products from file");
        }

        for (Product product : products) {
            addProduct(product);
        }
    }

    public void addProduct(Product product)
    {
        if (productNames.contains(product.getName())) {
            throw new IllegalArgumentException("Product name already exists" + product.getName());
        }
        this.arrayList.add(product);
        this.linkedList.add(product);
        this.productNames.add(product.getName());
        this.hashMap.put(product.getName(), product);
    }

    public void removeProduct(String productName)
    {
        Product product = this.hashMap.get(productName);
        if (product != null) {
            this.arrayList.remove(product);
            this.linkedList.remove(product);
            this.productNames.remove(productName);
            this.hashMap.remove(productName);
        }
    }
}
