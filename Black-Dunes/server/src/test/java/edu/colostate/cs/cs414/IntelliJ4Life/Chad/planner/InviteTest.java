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
        testInvite = new Invite("Test Invite");
    }

    @Test
    void testAcceptWithPendingInvitation() {
        assertTrue(testInvite.accept());
    }

    @Test
    void testAcceptWithAcceptedInvitation() {
        testInvite.accept();
        assertFalse(testInvite.accept());
    }

    @Test
    void testInviteUsersReturnsTrueWithPendingInvitation() {
        User testUser1 = mock(User.class);
        User testUser2 = mock(User.class);
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(testUser1);
        testUsers.add(testUser2);

        assertTrue(testInvite.inviteUsers(testUsers));
        verify(testUser1, times(1)).receiveInvite(testInvite);
        verify(testUser2, times(1)).receiveInvite(testInvite);
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
        verify(testUser1, times(0)).receiveInvite(testInvite);
        verify(testUser2, times(0)).receiveInvite(testInvite);
    }
}