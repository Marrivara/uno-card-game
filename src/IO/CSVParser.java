package src.IO;

import src.BusinessLayer.model.*;
import src.BusinessLayer.model.Interfaces.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            String[] parts = line.split(";");
            if (parts.length >= 5) {
                String name = parts[0].trim();
                String id = name;
                String costStr = parts[1].trim().replace(',', '.');
                String weightStr = parts[2].trim().replace(',', '.');
                String type = parts[3].trim();
                String stockStr = parts[4].trim().split(" ")[0];

                double price = Double.parseDouble(costStr);
                double weight = Double.parseDouble(weightStr);
                int stock = Integer.parseInt(stockStr);

                Component component;
                switch (type.toLowerCase()) {
                    case "raw material":
                        component = new RawMaterial(id, name, price, weight, stock);
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
        String line;

        String headerLine = reader.readLine();
        if (headerLine == null) {
            reader.close();
            throw new IOException("Empty products file");
        }

        String[] headers = headerLine.split(";");
        if (headers.length < 2) {
            reader.close();
            throw new IOException("Invalid products file format");
        }

        List<String> componentNames = new ArrayList<>();
        for (int i = 1; i < headers.length - 1; i++) {
            componentNames.add(headers[i].trim());
        }

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length < componentNames.size() + 2) {
                continue;
            }

            String productName = parts[0].trim();
            String id = productName;

            Product product = new Product(id, productName);

            for (int i = 1; i < parts.length - 1; i++) {
                if (i - 1 < componentNames.size()) {
                    String componentName = componentNames.get(i - 1);
                    String quantityStr = parts[i].trim().replace(',', '.');

                    if (!quantityStr.isEmpty() && !quantityStr.equals("0")) {
                        try {

                            double quantityDouble = Double.parseDouble(quantityStr);

                            Component component = inventory.getComponent(componentName);
                            if (component != null) {
                                product.addComponent(component, quantityDouble);
                                System.out.println("Added " + quantityDouble + "x " +
                                        component.getName() + " to " + product.getName());
                            } else {
                                System.out.println("Component not found: " + componentName);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid quantity for " + componentName + ": " + quantityStr);
                        }
                    }
                }
            }

            int orderQuantity = Integer.parseInt(parts[parts.length - 1].trim());
            orders.add(new ProductOrder(product, orderQuantity));
            System.out.println("Loaded product order: " + product.getName() +
                    " (Quantity: " + orderQuantity + ")\n");
        }

        reader.close();
        return orders;
    }

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