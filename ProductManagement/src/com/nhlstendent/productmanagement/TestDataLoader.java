package com.nhlstendent.productmanagement;

import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.MyHashMap;
import com.nhlstendent.productmanagement.ui.ProductTablePanel;

import javax.swing.*;
import java.awt.*;

// This class only for test loading function.
public class TestDataLoader
{
    public static void main(String[] args)
    {
        // Running GUI code in the EDT thread
        SwingUtilities.invokeLater(() ->
        {

            JFrame frame = new JFrame("testloading file");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 300);

            ProductTablePanel tablePanel = new ProductTablePanel();

            MyArrayList<MyHashMap<String, Object>> testData = createTestData();

            JButton loadButton = new JButton("loading data");
            loadButton.addActionListener(e ->
            {
                tablePanel.updateTable(testData);
            });

            frame.setLayout(new BorderLayout());
            frame.add(tablePanel, BorderLayout.CENTER);
            frame.add(loadButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    private static MyArrayList<MyHashMap<String, Object>> createTestData()
    {
        MyArrayList<MyHashMap<String, Object>> data = new MyArrayList<MyHashMap<String, Object>>();

        addProduct(data, "apple", 2.99, 4.5);
        addProduct(data, "banana", 1.99, 4.0);
        addProduct(data, "orange", 3.49, 4.2);
        addProduct(data, "cookie", 4.59, 2.1);
        addProduct(data, "bread", 0.99, 5.0);

        return data;
    }

    private static void addProduct(MyArrayList<MyHashMap<String, Object>> data,
                                   String name, double price, double rating)
    {
        MyHashMap<String, Object> product = new MyHashMap<String, Object>();
        product.put("name", name);
        product.put("price", price);
        product.put("rating", rating);
        data.add(product);
    }
} 