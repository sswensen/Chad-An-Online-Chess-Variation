package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationTest {
    @Test
    void testConstructorWithString() {
        String message = "Notification message";
        Notification testNotification = new Notification(message);
        assertEquals(message, testNotification.getMessage());
    }
}