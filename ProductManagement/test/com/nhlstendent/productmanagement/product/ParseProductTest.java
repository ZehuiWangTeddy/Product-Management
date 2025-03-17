package com.nhlstendent.productmanagement.product;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParseProductTest
{
    private ProductManager productManager;
    private static final String JSON_PATH = "Product.json";

    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
        productManager.importFromFile(JSON_PATH);
    }

    @Test
    public void testJsonImportWork_importFile_ShouldBeSuccessful() {
        assertFalse(productManager.getArrayList().isEmpty());
        assertFalse(productManager.getLinkedList().isEmpty());
        assertFalse(productManager.getHashMap().isEmpty());
        assertFalse(productManager.getProductNames().isEmpty());

        assertEquals(5, productManager.getArrayList().size());
        assertEquals(5, productManager.getHashMap().size());
        assertEquals(5, productManager.getLinkedList().size());
        assertEquals(5, productManager.getProductNames().size());
    }

    @Test
    public void testAddProductWork_addNewProduct_ShouldBeSuccessful() {
        Product newProduct = new Product("Grape", 4.99, 4.8);
        productManager.addProduct(newProduct);

        assertTrue(productManager.getArrayList().contains(newProduct));
        assertTrue(productManager.getHashMap().containsKey("Grape"));
        assertTrue(productManager.getProductNames().contains("Grape"));
        assertTrue(productManager.getLinkedList().contains(newProduct));
    }

    @Test
    public void testRemoveProductWork_removeProduct_ShouldBeSuccessful() {
        productManager.removeProduct("Apple");

        assertFalse(productManager.getProductNames().contains("Apple"));
        assertFalse(productManager.getHashMap().containsKey("Apple"));
        assertFalse(productManager.getArrayList().stream().anyMatch(p -> p.getName().equals("Apple")));
        assertFalse(productManager.getLinkedList().stream().anyMatch(p -> p.getName().equals("Apple")));
    }

    @Test
    public void testDuplicateProductWork_addProductExistInFile_ShouldBeSuccessful() {
        Product duplicate = new Product("Apple", 3.99, 4.5);
        assertThrows(IllegalArgumentException.class, () -> productManager.addProduct(duplicate));
    }
}
