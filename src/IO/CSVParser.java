package src.IO;

import src.BusinessLayer.model.*;
import src.BusinessLayer.model.Interfaces.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CSV parser utility (Pure Fabrication GRASP pattern)
public class CSVParser {
    public static Inventory loadComponents(String filename) throws IOException {
        Inventory inventory = new Inventory();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean headerSkipped = false;

        while ((line = reader.readLine()) != null) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue;
            }

            String[] parts = line.split("\t"); // Use tab as delimiter
            if (parts.length >= 5) {
                String name = parts[0].trim();
                // Use component name as ID (replace spaces with underscores)
                String id = name.replaceAll("\\s+", "_").toLowerCase();

                // Parse unit cost - replace comma with period for decimal
                double price = Double.parseDouble(parts[1].trim().replace(',', '.'));

                // Parse unit weight - replace comma with period for decimal
                double weight = Double.parseDouble(parts[2].trim().replace(',', '.'));

                String type = parts[3].trim();

                // Parse stock quantity - extract number only
                String stockStr = parts[4].trim();
                int stock = 0;
                // Extract numeric part of stock quantity
                StringBuilder numericPart = new StringBuilder();
                for (char c : stockStr.toCharArray()) {
                    if (Character.isDigit(c)) {
                        numericPart.append(c);
                    }
                }
                if (numericPart.length() > 0) {
                    stock = Integer.parseInt(numericPart.toString());
                }

                Component component;
                switch (type.toLowerCase()) {
                    case "raw material":
                        component = new
                                RawMaterial(id, name, price, weight, stock);
                        break;
                    case "paint":
                        component = new Paint(id, name, price, weight, stock);
                        break;
                    case "hardware":
                        component = new Hardware(id, name, price, weight, stock);
                        break;
                    default:
                        System.out.println("Unknown component type: " + type);
                        continue;
                }

                inventory.addComponent(component);
                System.out.println("Loaded component: " + component);
            }
        }

        reader.close();
        return inventory;
    }

    public static List<ProductOrder> loadProducts(String filename, Inventory inventory) throws IOException {
        List<ProductOrder> orders = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read header row to get component names
        String headerLine = reader.readLine();
        if (headerLine == null) {
            reader.close();
            throw new IOException("Empty products file");
        }

        String[] headers = headerLine.split("\t");

        // Map column indices to component IDs
        Map<Integer, String> columnToComponentId = new HashMap<>();
        for (int i = 1; i < headers.length - 1; i++) { // Skip first column (Product Name) and last column (Quantity)
            String componentName = headers[i].trim();
            String componentId = componentName.replaceAll("\\s+", "_").toLowerCase();
            columnToComponentId.put(i, componentId);
        }

        // Read product rows
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= headers.length) {
                String productName = parts[0].trim();
                String productId = "prod_" + productName.replaceAll("\\s+", "_").toLowerCase();

                Product product = new Product(productId, productName);

                // Parse component quantities
                for (int i = 1; i < parts.length - 1; i++) {
                    String quantityStr = parts[i].trim();
                    if (!quantityStr.isEmpty() && !quantityStr.equals("0")) {
                        // Replace comma with period for decimal
                        double quantity = Double.parseDouble(quantityStr.replace(',', '.'));
                        if (quantity > 0) {
                            String componentId = columnToComponentId.get(i);
                            Component component = inventory.getComponent(componentId);
                            if (component != null) {
                                // Convert to int (assuming we need integer quantities)
                                int intQuantity = (int)Math.ceil(quantity);
                                product.addComponent(component, intQuantity);
                                System.out.println("Added " + intQuantity + "x " +
                                        component.getName() + " to " + product.getName());
                            } else {
                                System.out.println("Component not found: " + componentId);
                            }
                        }
                    }
                }

                // Get order quantity from last column
                int orderQuantity = Integer.parseInt(parts[parts.length - 1].trim());
                orders.add(new ProductOrder(product, orderQuantity));
                System.out.println("Loaded product order: " + product.getName() +
                        " (Quantity: " + orderQuantity + ")");
            }
        }

        reader.close();
        return orders;
    }

    // ProductOrder class to store product orders (Pure Fabrication GRASP pattern)
    public static class ProductOrder {
        private Product product;
        private int quantity;

        public ProductOrder(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}