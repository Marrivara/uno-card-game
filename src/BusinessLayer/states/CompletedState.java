package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;

// Completed state
public class CompletedState implements ManufacturingState {
    @Override
    public void process(ManufacturingProcess process) {
        Product product = process.getProduct();
        product.increaseStock(1);
        System.out.println("Product completed and added to inventory: " + product.getName());
        process.setSuccessful(true);
    }

    @Override
    public String getStateName() {
        return "Completed";
    }
}