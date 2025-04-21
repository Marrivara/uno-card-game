package src.BusinessLayer;

import src.BusinessLayer.states.enums.ReasonOfFailure;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private int successfulCount;
    private int systemErrorCount;
    private int damagedComponentCount;
    private int notEnoughStockCount;
    private List<ProductResult> successfulProducts;

    public Report() {
        this.successfulCount = 0;
        this.systemErrorCount = 0;
        this.damagedComponentCount = 0;
        this.notEnoughStockCount = 0;
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
            String reason = process.getFailureReason();
            if (reason.equals(ReasonOfFailure.SYSTEM_ERROR.getDescription())) {
                systemErrorCount++;
            } else if (reason.equals(ReasonOfFailure.DAMAGED_COMPONENT.getDescription())) {
                damagedComponentCount++;
            } else if (reason.equals(ReasonOfFailure.NOT_ENOUGH_STOCK.getDescription())) {
                notEnoughStockCount++;
            }
        }
    }

    public void printReport() {
        System.out.println("\n***** Manufacturing Report *****");
        System.out.println("1. Successfully Manufactured Products: " + successfulCount);
        for (ProductResult result : successfulProducts) {
            System.out.printf("   - %s (Cost: $%.2f, Weight: %.2fkg)%n",
                    result.getName(),
                    result.getCost(),
                    result.getWeight());
        }

        System.out.println("2. Failed Because of System Error: " + systemErrorCount);
        System.out.println("3. Failed Because of Damaged Component: " + damagedComponentCount);
        System.out.println("4. Failed Because of Not Enough Stock: " + notEnoughStockCount);
        System.out.println("******************************");
    }

    // inner class
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