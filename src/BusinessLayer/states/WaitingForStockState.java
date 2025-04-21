package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;
import src.BusinessLayer.states.enums.ReasonOfFailure;

/**
 * WaitingForStockState class
 * This class represents the waiting for stock state of the manufacturing process.
 * It implements the ManufacturingState interface.
 */
public class WaitingForStockState implements ManufacturingState {
    @Override
    public void process(ManufacturingProcess process) {
        Product product = process.getProduct();
        System.out.println("Checking stock for: " + product.getName());

        if (product.areComponentsAvailable()) {
            System.out.println("All components available for " + product.getName());
            process.setState(new InManufacturingState());
        } else {
            System.out.println("Not enough stock for " + product.getName());
            process.setState(new FailedState(ReasonOfFailure.NOT_ENOUGH_STOCK));
        }
    }
}