package com.bispo.projecting.model.enums;

public enum TaskStatus {

    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    FINISHED("FINISHED");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
