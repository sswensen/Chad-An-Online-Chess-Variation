package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Invite extends Notification {
    private InviteStatus inviteStatus;

    public Invite(String message) {
        super(message);
    }

    /*************
     * Public Methods
     *************/
    public boolean accept() {
        /*
            This method will take a user and try to accept the invitation.
            If accepted is not set, the user will be added to the game.
         */
        return false;
    }

    public void reject() {
        /*
            This method will remove a user from the invite
         */
    }

    public boolean inviteUsers() {
        /*
            This method will take a list of users and return a bool if they can be added to the invite.
            They will not be added if the invite has not been accepted
         */
        return false;
    }
}
