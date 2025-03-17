package com.nhlstendent.productmanagement.ui;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Search Components
        JTextField searchField = createSearchField();
        JButton searchButton = createSearchButton();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        panel.add(searchField, gbc);
        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        // Sort Components
        JComboBox<String> sortDropdown = createSortDropdown();
        gbc.gridx = 3;
        panel.add(sortDropdown, gbc);

        // Upload Button
        JButton uploadButton = createUploadButton();
        gbc.gridx = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(uploadButton, gbc);

        return panel;
    }

    private JTextField createSearchField() {
        return new JTextField(15);
    }

    private JButton createSearchButton() {
        return new JButton("Search");
    }

    private JComboBox<String> createSortDropdown() {
        String[] sortOptions = {"Sort by Name", "Sort by Date"};
        return new JComboBox<>(sortOptions);
    }

    private JButton createUploadButton() {
        return new JButton("Upload File");
    }
}
