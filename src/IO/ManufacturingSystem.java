package src.IO;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.Report;
import src.BusinessLayer.model.Inventory;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.InManufacturingState;

import java.io.IOException;
import java.util.List;

// Main application (Controller GRASP pattern)
public class ManufacturingSystem {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Manufacturing Company System");

            // Load components and products from CSV files
            System.out.println("\nLoading components from components.csv...");
            Inventory inventory = CSVParser.loadComponents("components.csv");

            System.out.println("\nLoading products from products.csv...");
            List<CSVParser.ProductOrder> orders = CSVParser.loadProducts("products.csv", inventory);

            // Display initial inventory
            System.out.println("\nInitial Inventory:");
            inventory.printInventory();

            // Create report
            Report report = new Report();

            System.out.println("\nStarting manufacturing process...");
            // Process each product order sequentially
            for (CSVParser.ProductOrder order : orders) {
                Product product = order.getProduct();
                System.out.println("\nProcessing order for " + product.getName() +
                        " (Quantity: " + order.getQuantity() + ")");

                // Manufacture each product one by one
                for (int i = 0; i < order.getQuantity(); i++) {
                    System.out.println("\nManufacturing " + product.getName() + " (" + (i + 1) +
                            " of " + order.getQuantity() + ")");

                    // Create manufacturing process for the product
                    ManufacturingProcess process = new ManufacturingProcess(product);

                    // Process the manufacturing - Start in WaitingForStock state
                    process.process();

                    // If moved to InManufacturing state, continue processing
                    if (process.getState() instanceof InManufacturingState) {
                        process.process();
                    }

                    // If Completed or Failed, process the final state
                    process.process();

                    // Add result to report
                    report.addResult(process);

                    System.out.println("Manufacturing attempt completed with status: " +
                            (process.isSuccessful() ? "SUCCESS" : "FAILED - " +
                                    process.getFailureReason()));
                }
            }

            // Display final inventory
            System.out.println("\nFinal Inventory:");
            inventory.printInventory();

            // Print report
            report.printReport();

        } catch (IOException e) {
            System.err.println("Error reading CSV files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}