package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class Invite extends Notification {
    private InviteStatus inviteStatus;

    public Invite(String message) {
        super(message);
    }

    /*************
     * Public Methods
     *************/
    public boolean accept() {
       if (this.inviteStatus != InviteStatus.ACCEPTED) {
           this.inviteStatus = InviteStatus.ACCEPTED;
           return true;
       }
       else {
           return false;
       }
    }

    public boolean inviteUsers(ArrayList<User> users) {
        if (this.inviteStatus != InviteStatus.ACCEPTED) {
            for (User user: users) {
                user.receiveInvite(this);
            }
            return true;
        } else {
            return false;
        }
    }
}
