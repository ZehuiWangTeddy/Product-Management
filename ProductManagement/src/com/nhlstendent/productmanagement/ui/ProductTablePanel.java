package com.nhlstendent.productmanagement.ui;

import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.MyHashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ProductTablePanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ProductTablePanel() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Name", "Price", "Rating"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void updateTable(MyArrayList<MyHashMap<String, Object>> products) {
        tableModel.setRowCount(0);
        for (MyHashMap<String, Object> product : products) {
            tableModel.addRow(new Object[]{
                    product.get("name"),
                    product.get("price"),
                    product.get("rating")
            });
        }
    }
}