package com.bispo.projecting.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() {
        super("Project not found.");
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
