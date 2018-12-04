package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class User {
    private int userID;
    private String username, nickName, email;
    private ArrayList<Notification> receivedNotifications = new ArrayList<>();
    private ArrayList<Invite> sentInvites = new ArrayList<>();

    public User(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public User(int userID, String nickName, String email) {
        this.userID = userID;
        this.nickName = nickName;
        this.email = email;
    }

    public User(int userID, String username, String nickName, String email) {
        this.userID = userID;
        this.username = username;
        this.nickName = nickName;
        this.email = email;
    }

    public class Profile {
        private int wins, losses, draws;

        public Profile() {
            this.wins = 0;
            this.losses = 0;
            this.draws = 0;
        }

        /************
         * Accessors
         ***********/
        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getDraws() {
            return draws;
        }

        /*************
         * Public Methods
         *************/
        public void addWin() {
            this.wins++;
        }

        public void addLoss() {
            this.losses++;
        }

        public void addDraw() {
            this.draws++;
        }
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

    public ArrayList<Notification> getReceivedNotifications() {
        return receivedNotifications;
    }

    public ArrayList<Invite> getSentInvites() {
        return sentInvites;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void receiveNotification(Notification notification) {
        if (!receivedNotifications.contains(notification)) {
            this.receivedNotifications.add(notification);
        }
    }

    public boolean acceptInvitation(Invite invite) {
        if (receivedNotifications.contains(invite)) {
            boolean accepted = invite.accept();
            // This is where the user will be added to the game
            return accepted;
        }
        else {
            return false;
        }
    }

    public void rejectInvitation(Invite invite) {
        if (this.receivedNotifications.contains(invite)) {
            this.receivedNotifications.remove(invite);
            Notification reject = new Notification(this.nickName + " rejected your invite, loser.");
            invite.getSender().receiveNotification(reject);
        }
    }

    public boolean cancelInvitation(Invite invite) {
        return sentInvites.contains(invite) && invite.cancel();
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", receivedNotifications=" + receivedNotifications +
                ", sentInvites=" + sentInvites +
                '}';
    }
}
