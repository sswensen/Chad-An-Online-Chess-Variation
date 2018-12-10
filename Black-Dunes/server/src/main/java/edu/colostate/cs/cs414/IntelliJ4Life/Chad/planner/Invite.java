package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class Invite extends Notification {
    private InviteStatus inviteStatus;
    private User sender;

    /**
     * Object that represents an invite
     *
     * @param message
     * @param sender
     */
    public Invite(String message, User sender) {
        super(message);
        this.inviteStatus = InviteStatus.PENDING;
        this.sender = sender;
    }

    /**
     * Accessor that accesses the sender the of the invite
     *
     * @return
     */
    public User getSender() {
        return sender;
    }

    /**
     * Method to accept an invite
     *
     * @return
     */
    public boolean accept() {
        if(this.inviteStatus == InviteStatus.PENDING) {
            this.inviteStatus = InviteStatus.ACCEPTED;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to cancel an invite
     *
     * @return
     */
    public boolean cancel() {
        if(this.inviteStatus == InviteStatus.PENDING) {
            this.inviteStatus = InviteStatus.CANCELLED;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to invite a list of users
     *
     * @param users
     * @return
     */
    public boolean inviteUsers(ArrayList<User> users) {
        if(this.inviteStatus == InviteStatus.PENDING) {
            for(User user : users) {
                user.receiveNotification(this);
            }
            return true;
        } else {
            return false;
        }
    }
}
