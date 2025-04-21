package src.BusinessLayer.model.Interfaces;

// Component interface (base of the Composite Pattern)
public interface Component {
    String getId();
    String getName();
    double getPrice();
    double getWeight();
    boolean anyInStockByAmount(int quantity);
    void removeStockByAmount(int quantity);
    void addStockByAmount(int quantity);
}

