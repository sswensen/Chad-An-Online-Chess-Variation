package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {
    private User testUser;
    private User testInvitedUser;

    @Mock
    private Invite testInvite;

    @BeforeEach
    void setUp() {
        testUser = new User("TestSubject", "testSubject@test.com");
        testInvitedUser = new User("TestSubject2", "testSubject2@test.com");
        testInvite = mock(Invite.class);
    }

    @Test
    void testSendInviteReturnsTrueWithPendingInvite() {
        // Initialize
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(testInvitedUser);

        // User can be added to invite
        when(testInvite.inviteUsers(testUsers)).thenReturn(true);

        // The user will send the invite and it will be added to the list
        assertTrue(testUser.sendInvite(testInvite, testUsers));
        assertTrue(testUser.getSentInvites().contains(testInvite));
    }

    @Test
    void testSendInviteReturnsFalseWithAcceptedInvite() {
        // Initialize
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(testInvitedUser);

        // User cannot be added to invite
        when(testInvite.inviteUsers(testUsers)).thenReturn(false);

        // User will send the invite and it will be added to the list
        assertFalse(testUser.sendInvite(testInvite, testUsers));
        assertFalse(testUser.getSentInvites().contains(testInvite));
    }

    @Test
    void testReceiveInviteFirstTime() {
        testUser.receiveInvite(testInvite);
        assertTrue(testUser.getReceivedInvites().contains(testInvite));
    }

    @Test
    void testReceiveInviteSecondTime() {
        // Precondition: invite is received already
        testUser.receiveInvite(testInvite);
        assertTrue(testUser.getReceivedInvites().contains(testInvite));

        // Second time should not change anything
        testUser.receiveInvite(testInvite);
        assertTrue(testUser.getReceivedInvites().contains(testInvite));
    }

    @Test
    void testAcceptInvitationReturnsTrueWhenInvitationIsAccepted() {
        // Precondition: invite is received already
        testUser.receiveInvite(testInvite);
        assertTrue(testUser.getReceivedInvites().contains(testInvite));

        // User can accept the invitation
        when(testInvite.accept()).thenReturn(true);

        // Will return true
        assertTrue(testUser.acceptInvitation(testInvite));
    }

    @Test
    void testAcceptInvitationReturnsFalseWhenInvitationCannotBeAccepted() {
        // Precondition: invite is received already
        testUser.receiveInvite(testInvite);
        assertTrue(testUser.getReceivedInvites().contains(testInvite));

        // User can accept the invitation
        when(testInvite.accept()).thenReturn(false);

        // Will return true
        assertFalse(testUser.acceptInvitation(testInvite));
    }

    @Test
    void testAcceptInvitationReturnsFalseForNonReceivedInvite() {
        assertFalse(testUser.acceptInvitation(testInvite));
    }

}