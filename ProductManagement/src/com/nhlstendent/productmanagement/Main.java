package com.nhlstendent.productmanagement;

import com.nhlstendent.productmanagement.ui.MainGUI;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}