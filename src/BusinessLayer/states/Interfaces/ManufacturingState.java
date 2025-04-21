package src.BusinessLayer.states.Interfaces;

import src.BusinessLayer.ManufacturingProcess;

// State interface (State Pattern)
public interface ManufacturingState {
    void process(ManufacturingProcess process);
    String getStateName();
}







