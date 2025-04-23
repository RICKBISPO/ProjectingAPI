package com.bispo.projecting.model.enums;

public enum ProjectStatus {

    FINISHED("FINISHED"),
    IN_PROGRESS("IN_PROGRESS"),
    TO_START("TO_START");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
