package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class Invite extends Notification {
    private InviteStatus inviteStatus;
    private User sender;

    public Invite(String message, User sender) {
        super(message);
        this.inviteStatus = InviteStatus.PENDING;
        this.sender = sender;
    }

    /************
     * Accessors
     ***********/
    public User getSender() {
        return sender;
    }

    /*************
     * Public Methods
     *************/
    public boolean accept() {
       if (this.inviteStatus == InviteStatus.PENDING) {
           this.inviteStatus = InviteStatus.ACCEPTED;
           return true;
       }
       else {
           return false;
       }
    }

    public boolean cancel() {
        if (this.inviteStatus == InviteStatus.PENDING) {
            this.inviteStatus = InviteStatus.CANCELLED;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean inviteUsers(ArrayList<User> users) {
        if (this.inviteStatus ==  InviteStatus.PENDING) {
            for (User user: users) {
                user.receiveNotification(this);
            }
            return true;
        } else {
            return false;
        }
    }
}
