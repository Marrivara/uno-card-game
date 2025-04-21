package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.states.Interfaces.ManufacturingState;

// Failed state
public class FailedState implements ManufacturingState {
    private String reason;
    
    public FailedState(String reason) {
        this.reason = reason;
    }
    
    @Override
    public void process(ManufacturingProcess process) {
        System.out.println("Manufacturing failed: " + reason);
        process.setSuccessful(false);
        process.setFailureReason(reason);
    }
    
    @Override
    public String getStateName() {
        return "Failed: " + reason;
    }
    
    public String getReason() {
        return reason;
    }
}