package src.BusinessLayer.model.Interfaces;

/**
 * Interface for components in the system.
 */
public interface Component {
    String getId();
    String getName();
    double getPrice();
    double getWeight();
    boolean doesExists(int quantity);
    void decreaseStockByQuantity(int quantity);
    void increaseStockByQuantity(int quantity);
}