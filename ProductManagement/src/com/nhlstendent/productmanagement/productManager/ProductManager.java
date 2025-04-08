package com.nhlstendent.productmanagement.productManager;

import com.nhlstendent.productmanagement.model.*;
import com.nhlstendent.productmanagement.util.JsonUtil;

public class ProductManager
{
    private MyArrayList<Product> arrayList; // for sorting and linear search
    private MyLinkedList<Product> linkedList; // manually add product
    private MyHashSet<String> productNames; // import file
    private MyHashMap<String, Product> hashMap; // binary search

    public ProductManager()
    {
        this.arrayList = new MyArrayList<>();
        this.linkedList = new MyLinkedList<>();
        this.productNames = new MyHashSet<>();
        this.hashMap = new MyHashMap<>();
    }

    public MyArrayList<Product> getArrayList()
    {
        return arrayList;
    }

    public void setArrayList(MyArrayList<Product> arrayList)
    {
        this.arrayList = arrayList;
    }

    public MyLinkedList<Product> getLinkedList()
    {
        return linkedList;
    }

    public void setLinkedList(MyLinkedList<Product> linkedList)
    {
        this.linkedList = linkedList;
    }

    public MyHashSet<String> getProductNames()
    {
        return productNames;
    }

    public void setProductNames(MyHashSet<String> productNames)
    {
        this.productNames = productNames;
    }

    public MyHashMap<String, Product> getHashMap()
    {
        return hashMap;
    }

    public void setHashMap(MyHashMap<String, Product> hashMap)
    {
        this.hashMap = hashMap;
    }

    public void importFromFile(String filePath)
    {
        MyArrayList<Product> products = JsonUtil.parseProduct(filePath);
        if (products == null)
        {
            throw new RuntimeException("Failed to load products from file");
        }

        for (Product product : products)
        {
            addProduct(product);
        }
    }

    public void addProduct(Product product)
    {
        if (productNames.contains(product.getName()))
        {
            throw new IllegalArgumentException("Product name already exists: " + product.getName());
        }
        this.arrayList.add(product);
        this.linkedList.add(product);
        this.productNames.add(product.getName());
        this.hashMap.put(product.getName(), product);
    }

    public void removeProduct(String productName)
    {
        Product product = this.hashMap.get(productName);
        if (product != null)
        {
            this.arrayList.remove(product);
            this.linkedList.remove(product);
            this.productNames.remove(productName);
            this.hashMap.remove(productName);
        }
    }
}
