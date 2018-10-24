package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class User {
    private String nickName, email;
    private ArrayList<Invite> recievedInvites = new ArrayList<>();
    private ArrayList<Invite> sentInvites = new ArrayList<>();

    public User(String name, String email) {
        this.nickName = name;
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

    /*************
     * Public Methods
     *************/
    public void sendInvite(Invite invite) {
        this.sentInvites.add(invite);
    }

    public void recieveInvite(Invite invite) {
        this.recievedInvites.add(invite);
    }
}
