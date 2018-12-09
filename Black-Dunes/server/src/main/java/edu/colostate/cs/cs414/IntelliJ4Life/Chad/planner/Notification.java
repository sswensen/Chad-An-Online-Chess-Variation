package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Notification {
    private String message;

    /**
     * Constructor for notification object
     *
     * @param message - message for notification
     */
    public Notification(String message) {
        this.message = message;
    }

    /************
     * Accessors
     ***********/
    /**
     * Gets the message for the notification
     */
    public String getMessage() {
        return this.message;
    }
}
