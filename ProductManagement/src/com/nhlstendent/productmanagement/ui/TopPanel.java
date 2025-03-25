package com.nhlstendent.productmanagement.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TopPanel extends JPanel {
    private JTextField searchField;

    public TopPanel(ActionListener uploadHandler,
                    ActionListener searchHandler,
                    ActionListener sortHandler,
                    ActionListener resetHandler) {

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Upload Button
        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(uploadHandler);
        add(uploadButton);

        // Search Components
        add(new JLabel("Search:"));
        searchField = new JTextField(15);
        add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(searchHandler);
        add(searchButton);

        // Sort by Price Button
        JButton sortButton = new JButton("Sort by Price");
        sortButton.addActionListener(sortHandler);
        add(sortButton);

        // Reset Button
        JButton resetButton = new JButton("RESET");
        resetButton.addActionListener(resetHandler);
        add(resetButton);
    }

    public String getSearchText() {
        return searchField.getText();
    }
}