package src.BusinessLayer.model;

// Product (Composite) implementation
import src.BusinessLayer.model.Interfaces.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Product implements Component {
    private String id;
    private String name;
    private int stock;
    private Map<Component, Integer> components; // Component and quantity needed
    
    public Product(String id, String name) {
        this.id = id;
        this.name = name;
        this.stock = 0;
        this.components = new HashMap<>();
    }
    
    public void addComponent(Component component, int quantity) {
        components.put(component, quantity);
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public double getPrice() {
        double total = 0;
        for (Map.Entry<Component, Integer> entry : components.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    
    @Override
    public double getWeight() {
        double total = 0;
        for (Map.Entry<Component, Integer> entry : components.entrySet()) {
            total += entry.getKey().getWeight() * entry.getValue();
        }
        return total;
    }
    
    @Override
    public boolean anyInStockByAmount(int quantity) {
        return stock >= quantity;
    }
    
    @Override
    public void removeStockByAmount(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock for " + name);
        }
    }
    
    @Override
    public void addStockByAmount(int quantity) {
        stock += quantity;
    }
    
    public Map<Component, Integer> getComponents() {
        return Collections.unmodifiableMap(components);
    }
    
    public boolean areComponentsAvailable() {
        for (Map.Entry<Component, Integer> entry : components.entrySet()) {
            if (!entry.getKey().anyInStockByAmount(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public void consumeComponents() {
        for (Map.Entry<Component, Integer> entry : components.entrySet()) {
            entry.getKey().removeStockByAmount(entry.getValue());
        }
    }
    
    @Override
    public String toString() {
        return name + " (Stock: " + stock + ")";
    }
}