package src.BusinessLayer.states.enums;

public enum ReasonOfFailure {
    NOT_ENOUGH_STOCK("Not Enough Stock"),
    SYSTEM_ERROR("System Error"),
    DAMAGED_COMPONENT("Damaged Component");

    private final String description;

    ReasonOfFailure(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}