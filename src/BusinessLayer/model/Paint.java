package src.BusinessLayer.model;

/**
 * Represents a paint component in the system.
 */
public class Paint extends BasicComponent {
    public Paint(String id, String name, double price, double weight, int stock) {
        super(id, name, price, weight, stock);
    }
}