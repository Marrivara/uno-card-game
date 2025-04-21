package src.BusinessLayer.states.Interfaces;

import src.BusinessLayer.ManufacturingProcess;

/**
 * ManufacturingState interface
 * This interface defines the method that all states must implement.
 */
public interface ManufacturingState {
    void process(ManufacturingProcess process);
}







