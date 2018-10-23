package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @org.junit.jupiter.api.Test
    void testConstructorWithString() {
        String message = "Notification message";
        Notification testNotification = new Notification(message);
        assertEquals(message, testNotification.getMessage());
    }

}