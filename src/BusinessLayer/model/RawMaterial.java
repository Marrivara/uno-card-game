package src.BusinessLayer.model;

/**
 * Represents a raw material component in the system.
 */
public class RawMaterial extends BasicComponent {
    public RawMaterial(String id, String name, double price, double weight, int stock) {
        super(id, name, price, weight, stock);
    }
}