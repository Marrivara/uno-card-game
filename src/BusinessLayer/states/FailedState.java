package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.states.Interfaces.ManufacturingState;
import src.BusinessLayer.states.enums.FailureReason;

// Failed state
public class FailedState implements ManufacturingState {
    private FailureReason reason;

    public FailedState(FailureReason reason) {
        this.reason = reason;
    }

    @Override
    public void process(ManufacturingProcess process) {
        System.out.println("Manufacturing failed: " + reason.getDescription());
        process.setSuccessful(false);
        process.setFailureReason(reason.getDescription());
    }

    @Override
    public String getStateName() {
        return "Failed: " + reason.getDescription();
    }

    public FailureReason getReason() {
        return reason;
    }
}