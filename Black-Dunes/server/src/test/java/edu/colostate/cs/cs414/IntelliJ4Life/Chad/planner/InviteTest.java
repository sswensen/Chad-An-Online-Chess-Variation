package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InviteTest {
    private Invite testInvite;

    @BeforeEach
    void setUp() {
        testInvite = new Invite("Test Invite", new User("Test Man", "test@test.com"));
    }

    @Test
    void testAcceptReturnsTrueWithPendingInvitation() {
        assertTrue(testInvite.accept());
    }

    @Test
    void testAcceptReturnsFalseWithCancelledInvitation() {
        testInvite.cancel();
        assertFalse(testInvite.accept());
    }

    @Test
    void testAcceptReturnsFalseWithAcceptedInvitation() {
        testInvite.accept();
        assertFalse(testInvite.accept());
    }

    @Test
    void testCancelReturnsTrueWithPendingInvitation() {
        assertTrue(testInvite.cancel());
    }

    @Test
    void testCancelReturnsFalseWithAcceptedInvitation() {
        testInvite.accept();
        assertFalse(testInvite.cancel());
    }

    @Test
    void testCancelReturnsFalseWithCancelledInvitation() {
        testInvite.cancel();
        assertFalse(testInvite.cancel());
    }

    @Test
    void testInviteUsersReturnsTrueWithPendingInvitation() {
        User testUser1 = mock(User.class);
        User testUser2 = mock(User.class);
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(testUser1);
        testUsers.add(testUser2);

        assertTrue(testInvite.inviteUsers(testUsers));
        verify(testUser1, times(1)).receiveNotification(testInvite);
        verify(testUser2, times(1)).receiveNotification(testInvite);
    }

    @Test
    void testInviteUsersReturnsFalseWithAcceptedInvitation() {
        User testUser1 = mock(User.class);
        User testUser2 = mock(User.class);
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(testUser1);
        testUsers.add(testUser2);

        testInvite.accept();

        assertFalse(testInvite.inviteUsers(testUsers));
        verify(testUser1, times(0)).receiveNotification(testInvite);
        verify(testUser2, times(0)).receiveNotification(testInvite);
    }
}