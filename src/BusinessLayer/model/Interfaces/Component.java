package src.BusinessLayer.model.Interfaces;

public interface Component {
    String getId();
    String getName();
    double getPrice();
    double getWeight();
    boolean doesExists(int quantity);
    void decreaseStockByQuantity(int quantity);
    void increaseStockByQuantity(int quantity);
}