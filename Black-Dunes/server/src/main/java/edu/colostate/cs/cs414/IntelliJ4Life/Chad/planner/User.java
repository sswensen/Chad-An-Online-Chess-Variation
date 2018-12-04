package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class User {
    private int userID;
    private String username, nickName, email;
    private ArrayList<Notification> receivedNotifications = new ArrayList<>();
    private ArrayList<Invite> sentInvites = new ArrayList<>();

    /**
     * Object that represents a user
     * @param nickName
     * @param email
     */
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

    /**
     * Inner class that holds the wins losses and draws for a user*
     */
    public class Profile {
        private int wins, losses, draws;

        public Profile() {
            this.wins = 0;
            this.losses = 0;
            this.draws = 0;
        }

        /**
         * Accessors to get wins, losses, and draws for a user
         * @return
         */
        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getDraws() {
            return draws;
        }

        /**
         * Setters to add a win, loss, or draw to a user
         */
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

    /**
     * Accessor to get the nickname of user
     * @return
     */
    public String getNickName() { return nickName; }

    /**
     * Setter to change the nickname of user
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Accessor to get the email of a user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter to change the email of a user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accessor to get a list of all notifications received
     * @return
     */
    public ArrayList<Notification> getReceivedNotifications() {
        return receivedNotifications;
    }

    /**
     * Accessor to get a list of all sent invites
     * @return
     */
    public ArrayList<Invite> getSentInvites() {
        return sentInvites;
    }

    /**
     * Accessor to get unique userID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter to change userID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Accessor to get the usernam
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter to change the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to send an invite to a several users
     * @param invite Invite object being sent to users
     * @param users Arraylist of users that will receive an invite
     * @return
     */
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

    /**
     * Method to get specific notification from list of received notifications
     * @param notification
     */
    public void receiveNotification(Notification notification) {
        if (!receivedNotifications.contains(notification)) {
            this.receivedNotifications.add(notification);
        }
    }

    /**
     * Method that accepts an invite
     * @param invite
     * @return True if invite was successfully accepted, false otherwise
     */
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

    /**
     * Method that rejects a invite
     * @param invite
     */
    public void rejectInvitation(Invite invite) {
        if (this.receivedNotifications.contains(invite)) {
            this.receivedNotifications.remove(invite);
            Notification reject = new Notification(this.nickName + " rejected your invite, loser.");
            invite.getSender().receiveNotification(reject);
        }
    }

    /**
     * Method to cancel an invite
     * @param invite
     * @return
     */
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
