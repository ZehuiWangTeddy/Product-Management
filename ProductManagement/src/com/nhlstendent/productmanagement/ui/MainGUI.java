package com.nhlstendent.productmanagement.ui;

import com.nhlstendent.productmanagement.controller.ProductController;
import com.nhlstendent.productmanagement.model.MyArrayList;
import com.nhlstendent.productmanagement.model.MyHashMap;
import com.nhlstendent.productmanagement.ui.ProductTablePanel;
import com.nhlstendent.productmanagement.ui.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private final JFrame frame;
    private final ProductController controller;
    private final ProductTablePanel tablePanel;
    private final JLabel statusLabel;
    private final TopPanel topPanel;

    public MainGUI() {
        controller = new ProductController();
        frame = new JFrame("Product Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);

        tablePanel = new ProductTablePanel();
        statusLabel = new JLabel("Execution Time: ");

        topPanel = new TopPanel(
                new UploadHandler(),
                new SearchHandler(),
                new SortHandler(),
                new SortByLetterHandler(),
                new ResetHandler()
        );

        initUI();
    }

    private void initUI() {
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private class UploadHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadProductsFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                    tablePanel.updateTable(controller.getProducts());
                    statusLabel.setText("File loaded successfully");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Import Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error reading file: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class SearchHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = topPanel.getSearchText();
            long start = System.currentTimeMillis();
            MyArrayList<MyHashMap<String, Object>> results;

            try {
                double priceQuery = Double.parseDouble(query); // Check if query is a number (price)
                results = controller.binarySearchByPrice(priceQuery);
            } catch (NumberFormatException ex) {
                results = controller.linearSearch(query); // If not a number, perform linear search
            }

            long duration = System.currentTimeMillis() - start;

            if (!results.isEmpty() && results.get(0).containsKey("Sorry")) {
                statusLabel.setText("Error: " + results.get(0).get("Sorry") + " (" + duration + " ms)");
                tablePanel.updateTable(new MyArrayList<>());
            } else {
                tablePanel.updateTable(results);
                statusLabel.setText("Execution Time: " + duration + " ms | Results: " + results.size());
            }
        }
    }

    private class SortHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long start = System.currentTimeMillis();
            controller.sortProductsByPrice();
            long duration = System.currentTimeMillis() - start;

            tablePanel.updateTable(controller.getProducts());
            statusLabel.setText("Execution Time: " + duration + " ms");
        }
    }

    private class SortByLetterHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long start = System.currentTimeMillis();
            controller.sortProductsByName();
            long duration = System.currentTimeMillis() - start;

            tablePanel.updateTable(controller.getProducts());
            statusLabel.setText("Execution Time: " + duration + " ms");
        }
    }

    private class ResetHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.resetProducts();
            tablePanel.updateTable(controller.getProducts());
            statusLabel.setText("Execution Time: ");
        }
    }
}