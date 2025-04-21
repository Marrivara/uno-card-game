package src.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

// Report class to generate manufacturing report (Information Expert GRASP pattern)
public class Report {
    private int successfulCount;
    private int systemErrorCount;
    private int damagedComponentCount;
    private int stockShortageCount;
    private List<ProductResult> successfulProducts;
    
    public Report() {
        this.successfulCount = 0;
        this.systemErrorCount = 0;
        this.damagedComponentCount = 0;
        this.stockShortageCount = 0;
        this.successfulProducts = new ArrayList<>();
    }
    
    public void addResult(ManufacturingProcess process) {
        if (process.isSuccessful()) {
            successfulCount++;
            successfulProducts.add(new ProductResult(
                process.getProduct().getName(),
                process.getProduct().getPrice(),
                process.getProduct().getWeight()
            ));
        } else {
            switch (process.getFailureReason()) {
                case "System Error":
                    systemErrorCount++;
                    break;
                case "Damaged Component":
                    damagedComponentCount++;
                    break;
                case "Stock Shortage":
                    stockShortageCount++;
                    break;
            }
        }
    }
    
    public void printReport() {
        System.out.println("\n===== Manufacturing Report =====");
        System.out.println("1. Successfully Manufactured Products: " + successfulCount);
        for (ProductResult result : successfulProducts) {
            System.out.printf("   - %s (Cost: $%.2f, Weight: %.2fkg)%n", 
                              result.getName(), 
                              result.getCost(), 
                              result.getWeight());
        }
        
        System.out.println("2. Failed Due to System Error: " + systemErrorCount);
        System.out.println("3. Failed Due to Damaged Component: " + damagedComponentCount);
        System.out.println("4. Failed Due to Stock Shortage: " + stockShortageCount);
        System.out.println("=================================");
    }
    
    // Inner class to store successful product results
    private class ProductResult {
        private String name;
        private double cost;
        private double weight;
        
        public ProductResult(String name, double cost, double weight) {
            this.name = name;
            this.cost = cost;
            this.weight = weight;
        }
        
        public String getName() {
            return name;
        }
        
        public double getCost() {
            return cost;
        }
        
        public double getWeight() {
            return weight;
        }
    }
}