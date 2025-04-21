package src.BusinessLayer.model;

// Raw Material implementation
public class RawMaterial extends SimpleComponent {
    public RawMaterial(String id, String name, double price, double weight, int stock) {
        super(id, name, price, weight, stock);
    }
}