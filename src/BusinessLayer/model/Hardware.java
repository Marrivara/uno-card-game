package src.BusinessLayer.model;

/**
 * Represents a hardware component in the system.
 */
public class Hardware extends BasicComponent {
    public Hardware(String id, String name, double price, double weight, int stock) {
        super(id, name, price, weight, stock);
    }
}