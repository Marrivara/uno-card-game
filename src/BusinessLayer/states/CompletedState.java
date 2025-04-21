package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;

/**
 * CompletedState class
 * This class represents the completed state of the manufacturing process.
 * It implements the ManufacturingState interface.
 */
public class CompletedState implements ManufacturingState {
    @Override
    public void process(ManufacturingProcess process) {
        Product product = process.getProduct();
        product.increaseStockByQuantity(1);
        System.out.println("Product completed and added to inventry: " + product.getName());
        process.setSuccessful(true);
    }
}