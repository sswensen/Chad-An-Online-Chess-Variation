package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Notification {
    private String message;

    public Notification(String message) {
        this.message = message;
    }

    /************
     * Accessors
     ***********/
    public String getMessage() {
        return this.message;
    }
}
