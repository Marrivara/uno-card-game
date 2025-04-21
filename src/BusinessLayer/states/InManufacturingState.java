package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;
import src.BusinessLayer.states.enums.ReasonOfFailure;

import java.util.Random;

/**
 * InManufacturingState class
 * This class represents the in-manufacturing state of the manufacturing process.
 * It implements the ManufacturingState interface.
 */
public class InManufacturingState implements ManufacturingState {
    private Random random = new Random();

    @Override
    public void process(ManufacturingProcess process) {
        Product product = process.getProduct();
        System.out.println("Manufacturing: " + product.getName());

        if (!product.areComponentsAvailable()) {
            System.out.println("Not enough stock during manufacturing for " + product.getName());
            process.setState(new FailedState(ReasonOfFailure.NOT_ENOUGH_STOCK));
            return;
        }

        int outcome = random.nextInt(3) + 1;
        switch (outcome) {
            case 1:
                System.out.println("Successfully manufactured " + product.getName());
                product.consumeComponents();
                process.setState(new CompletedState());
                break;
            case 2:
                System.out.println("System error during manufacturing of " + product.getName());
                process.setState(new FailedState(ReasonOfFailure.SYSTEM_ERROR));
                break;
            case 3:
                System.out.println("Component damaged during manufacturing of " + product.getName());
                process.setState(new FailedState(ReasonOfFailure.DAMAGED_COMPONENT));
                break;
        }
    }
}