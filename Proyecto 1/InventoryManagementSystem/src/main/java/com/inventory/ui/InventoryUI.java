package com.inventory.ui;

import com.inventory.model.Product;
import com.inventory.service.InventoryService;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class InventoryUI {
    private InventoryService inventoryService;
    private Scanner scanner;

    public InventoryUI() {
        this.inventoryService = new InventoryService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===== Inventory Management System =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Product");
            System.out.println("5. View All Products");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    viewProduct();
                    break;
                case 5:
                    viewAllProducts();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addProduct() {
        System.out.println("\n----- Add New Product -----");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        Product product = new Product(0, name, quantity, price);
        try {
            inventoryService.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private void updateProduct() {
        System.out.println("\n----- Update Product -----");
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Product product = inventoryService.getProduct(id);
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.print("Enter new name (current: " + product.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new quantity (current: " + product.getQuantity() + "): ");
            int quantity = scanner.nextInt();
            System.out.print("Enter new price (current: " + product.getPrice() + "): ");
            double price = scanner.nextDouble();

            product.setName(name);
            product.setQuantity(quantity);
            product.setPrice(price);

            inventoryService.updateProduct(product);
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        System.out.println("\n----- Delete Product -----");
        System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();

        try {
            inventoryService.deleteProduct(id);
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    private void viewProduct() {
        System.out.println("\n----- View Product -----");
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        try {
            Product product = inventoryService.getProduct(id);
            if (product != null) {
                System.out.println("ID: " + product.getId());
                System.out.println("Name: " + product.getName());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("Price: " + product.getPrice());
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing product: " + e.getMessage());
        }
    }

    private void viewAllProducts() {
        System.out.println("\n----- All Products -----");
        try {
            List<Product> products = inventoryService.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                for (Product product : products) {
                    System.out.println("ID: " + product.getId() +
                            ", Name: " + product.getName() +
                            ", Quantity: " + product.getQuantity() +
                            ", Price: " + product.getPrice());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing products: " + e.getMessage());
        }
    }
}