package src.BusinessLayer.states.enums;

public enum FailureReason {
    STOCK_SHORTAGE("Stock Shortage"),
    SYSTEM_ERROR("System Error"),
    DAMAGED_COMPONENT("Damaged Component");

    private final String description;

    FailureReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}