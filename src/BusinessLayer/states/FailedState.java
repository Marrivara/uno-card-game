package src.BusinessLayer.states;

import src.BusinessLayer.ManufacturingProcess;
import src.BusinessLayer.states.Interfaces.ManufacturingState;
import src.BusinessLayer.states.enums.ReasonOfFailure;

/**
 * FailedState class
 * This class represents the failed state of the manufacturing process.
 * It implements the ManufacturingState interface.
 */
public class FailedState implements ManufacturingState {
    private ReasonOfFailure reason;

    public FailedState(ReasonOfFailure reason) {
        this.reason = reason;
    }

    @Override
    public void process(ManufacturingProcess process) {
        System.out.println("Manufacturing failed: " + reason.getDescription());
        process.setSuccessful(false);
        process.setFailureReason(reason.getDescription());
    }
}