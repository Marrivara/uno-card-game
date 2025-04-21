package src.BusinessLayer;

import src.BusinessLayer.model.Inventory;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.InManufacturingState;
import src.IO.CSVParser;

import java.io.IOException;
import java.util.List;

public class ManufacturingSystem {

    public void start() {
        try {
            System.out.println("Starting Manufacturing Company System");

            System.out.println("\nLoading components from components.csv...");
            Inventory inventory = CSVParser.loadComponents("components.csv");

            System.out.println("\nLoading products from products.csv...");
            List<CSVParser.ProductOrder> orders = CSVParser.loadProducts("products.csv", inventory);

            System.out.println("\nInitial Inventory:");
            inventory.printInventory();

            Report report = new Report();

            System.out.println("\nStarting manufacturing process...");

            for (CSVParser.ProductOrder order : orders) {
                Product product = order.getProduct();
                System.out.println("\nProcessing order for " + product.getName() +
                        " (Quantity: " + order.getQuantity() + ")");

                for (int i = 0; i < order.getQuantity(); i++) {
                    System.out.println("\nManufacturing " + product.getName() + " (" + (i + 1) +
                            " of " + order.getQuantity() + ")");

                    ManufacturingProcess process = new ManufacturingProcess(product);

                    process.process();

                    if (process.getState() instanceof InManufacturingState) {
                        process.process();
                    }

                    process.process();

                    report.addResult(process);

                    System.out.println("Manufacturing attempt completed with status: " +
                            (process.isSuccessful() ? "SUCCESS" : "FAILED - " +
                                    process.getFailureReason()));
                }
            }

            System.out.println("\nFinal Inventory:");
            inventory.printInventory();

            report.printReport();

        } catch (IOException e) {
            System.err.println("Error reading CSV files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}