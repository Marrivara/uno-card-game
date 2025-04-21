package src.IO;

import src.BusinessLayer.model.*;
import src.BusinessLayer.model.Interfaces.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String id = parts[0].trim();
                String name = parts[1].trim();
                String type = parts[2].trim().toLowerCase();
                double price = Double.parseDouble(parts[3].trim());
                double weight = Double.parseDouble(parts[4].trim());
                int stock = Integer.parseInt(parts[5].trim());
                
                Component component;
                switch (type) {
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
        boolean headerSkipped = false;
        
        while ((line = reader.readLine()) != null) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue;
            }
            
            String[] parts = line.split(",", -1); // Use -1 to keep empty trailing fields
            if (parts.length >= 4) {
                String id = parts[0].trim();
                String name = parts[1].trim();
                
                Product product = new Product(id, name);
                
                // Parse component IDs and quantities
                String componentsStr = parts[2].trim();
                if (!componentsStr.isEmpty()) {
                    String[] componentParts = componentsStr.split(";");
                    for (String componentPart : componentParts) {
                        String[] componentDetails = componentPart.split(":");
                        if (componentDetails.length >= 2) {
                            String componentId = componentDetails[0].trim();
                            int quantity = Integer.parseInt(componentDetails[1].trim());
                            
                            Component component = inventory.getComponent(componentId);
                            if (component != null) {
                                product.addComponent(component, quantity);
                                System.out.println("Added " + quantity + "x " + 
                                                  component.getName() + " to " + product.getName());
                            } else {
                                System.out.println("Component not found: " + componentId);
                            }
                        }
                    }
                }
                
                int orderQuantity = Integer.parseInt(parts[3].trim());
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