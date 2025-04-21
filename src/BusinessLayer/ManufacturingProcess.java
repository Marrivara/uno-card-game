package src.BusinessLayer;

import src.BusinessLayer.model.Product;
import src.BusinessLayer.states.Interfaces.ManufacturingState;
import src.BusinessLayer.states.WaitingForStockState;

/**
 * ManufacturingProcess class
 * This class represents the manufacturing process of a product.
 */
public class ManufacturingProcess {
    private Product product;
    private ManufacturingState state;
    private boolean successful;
    private String failureReason;
    
    public ManufacturingProcess(Product product) {
        this.product = product;
        this.state = new WaitingForStockState();
        this.successful = false;
        this.failureReason = "";
    }
    
    public void process() {
        state.process(this);
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setState(ManufacturingState state) {
        this.state = state;
    }
    
    public ManufacturingState getState() {
        return state;
    }
    
    public boolean isSuccessful() {
        return successful;
    }
    
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
    
    public String getFailureReason() {
        return failureReason;
    }
    
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}