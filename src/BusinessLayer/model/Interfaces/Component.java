package src.BusinessLayer.model.Interfaces;

// Component interface (base of the Composite Pattern)
public interface Component {
    String getId();
    String getName();
    double getPrice();
    double getWeight();
    boolean isAvailable(int quantity);
    void decreaseStock(int quantity);
    void increaseStock(int quantity);
}