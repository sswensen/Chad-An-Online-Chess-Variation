package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class User {
    private String nickName, email;
    private ArrayList<Invite> receivedInvites = new ArrayList<>();
    private ArrayList<Invite> sentInvites = new ArrayList<>();

    public User(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    /************
     * Accessors
     ***********/
    public String getNickName() { return nickName; }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Invite> getReceivedInvites() {
        return receivedInvites;
    }

    public ArrayList<Invite> getSentInvites() {
        return sentInvites;
    }

    /*************
     * Public Methods
     *************/
    public boolean sendInvite(Invite invite, ArrayList<User> users) {
        if (invite.inviteUsers(users)) {
            if (!sentInvites.contains(invite)) {
                this.sentInvites.add(invite);
            }
            return true;
        } else {
            return false;
        }
    }

    public void receiveInvite(Invite invite) {
        if (!receivedInvites.contains(invite)) {
            this.receivedInvites.add(invite);
        }
    }

    public boolean acceptInvitation(Invite invite) {
        if (receivedInvites.contains(invite)) {
            boolean accepted = invite.accept();
            // This is where the user will be added to the game
            return accepted;
        }
        else {
            return false;
        }
    }
}
