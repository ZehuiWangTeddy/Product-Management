package com.nhlstendent.productmanagement.ui;

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

    public void updateTable(List<Map<String, Object>> products) {
        tableModel.setRowCount(0);
        for (Map<String, Object> product : products) {
            tableModel.addRow(new Object[]{
                    product.get("name"),
                    product.get("price"),
                    product.get("rating")
            });
        }
    }
}