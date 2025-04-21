package src.BusinessLayer.model;

import src.BusinessLayer.model.Interfaces.Component;

/**
 * Abstract class representing a basic component in the system.
 */
public abstract class BasicComponent implements Component {
    protected String id;
    protected String name;
    protected double price;
    protected double weight;
    protected int stock;

    public BasicComponent(String id, String name, double price, double weight, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.stock = stock;
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
        return price;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean doesExists(int quantity) {
        return stock >= quantity;
    }

    @Override
    public void decreaseStockByQuantity(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough stock for " + name);
        }
    }

    @Override
    public void increaseStockByQuantity(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return name + " (Stock: " + stock + ")";
    }
}