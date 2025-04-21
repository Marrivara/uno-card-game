package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;

import java.util.Random;

// InManufacturing state
public class InManufacturingState implements ManufacturingState {
    private Random random = new Random();
    
    @Override
    public void process(ManufacturingProcess process) {
        Product product = process.getProduct();
        System.out.println("Manufacturing: " + product.getName());
        
        // Check if components are available again (just to be safe)
        if (!product.areComponentsAvailable()) {
            System.out.println("Stock shortage detected during manufacturing for " + product.getName());
            process.setState(new FailedState("Stock Shortage"));
            return;
        }
        
        // Random manufacturing outcome (1-3)
        int outcome = random.nextInt(3) + 1;
        switch (outcome) {
            case 1:
                // Successful manufacturing
                System.out.println("Successfully manufactured " + product.getName());
                product.consumeComponents();
                process.setState(new CompletedState());
                break;
            case 2:
                // System error
                System.out.println("System error during manufacturing of " + product.getName());
                process.setState(new FailedState("System Error"));
                break;
            case 3:
                // Damaged component
                System.out.println("Component damaged during manufacturing of " + product.getName());
                process.setState(new FailedState("Damaged Component"));
                break;
        }
    }
    
    @Override
    public String getStateName() {
        return "InManufacturing";
    }
}