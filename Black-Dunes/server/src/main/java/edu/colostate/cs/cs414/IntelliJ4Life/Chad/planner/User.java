package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class User {
    private int userID;
    private String username, nickName, email;
    private ArrayList<Notification> receivedNotifications = new ArrayList<>();
    private ArrayList<Invite> sentInvites = new ArrayList<>();

    /**
     * Constructor for the User object
     *
     * @param nickName - nickname for the user
     * @param email    - email for the user
     */
    public User(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    /**
     * Constructor for the User object
     *
     * @param userID   - userId for the user
     * @param nickName - nickname for the user
     * @param email    - email for the user
     */
    public User(int userID, String nickName, String email) {
        this.userID = userID;
        this.nickName = nickName;
        this.email = email;
    }

    /**
     * Constructor for the User object
     *
     * @param userID   - userId for the user
     * @param username - username for the user
     * @param nickName - nickname for the user
     * @param email    - email for the user
     */
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
    /**
     * get the nickname for the user
     *
     * @return - nickname for the user
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * sets the nickname for the user
     *
     * @param nickName - name to set as the nickname
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * gets the email for the user
     *
     * @return email for the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email for the user
     *
     * @param email - email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the received notifications for the user
     *
     * @return - list of received notifications for the user
     */
    public ArrayList<Notification> getReceivedNotifications() {
        return receivedNotifications;
    }

    /**
     * Gets the sent invites for the user
     *
     * @return - The sent invites for the user
     */
    public ArrayList<Invite> getSentInvites() {
        return sentInvites;
    }

    /**
     * Gets the userID for the user
     *
     * @return userID for the user
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID for the user
     *
     * @param userID - set the userID for the user
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * gets the username for the user
     *
     * @return userName for the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username for the user
     *
     * @param username - username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*************
     * Public Methods
     *************/
    /**
     * send invite to all the users in the given list
     *
     * @param invite - Invite objcet to send
     * @param users  - list of users to send the invite too
     * @return - true if invites sent successfully, false otherwise
     */
    public boolean sendInvite(Invite invite, ArrayList<User> users) {
        if(invite.inviteUsers(users)) {
            if(!sentInvites.contains(invite)) {
                this.sentInvites.add(invite);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * receive notification sent by other player
     *
     * @param notification - notification to receive
     */
    public void receiveNotification(Notification notification) {
        if(!receivedNotifications.contains(notification)) {
            this.receivedNotifications.add(notification);
        }
    }

    /**
     * accepts invite sent by other user
     *
     * @param invite - invite to accept
     * @return - true if successfully accepted, false otherwisee
     */
    public boolean acceptInvitation(Invite invite) {
        if(receivedNotifications.contains(invite)) {
            boolean accepted = invite.accept();
            // This is where the user will be added to the game
            return accepted;
        } else {
            return false;
        }
    }

    /**
     * Rejects invite sent by other user
     *
     * @param invite - invite sent by other user
     */
    public void rejectInvitation(Invite invite) {
        if(this.receivedNotifications.contains(invite)) {
            this.receivedNotifications.remove(invite);
            Notification reject = new Notification(this.nickName + " rejected your invite, loser.");
            invite.getSender().receiveNotification(reject);
        }
    }

    /**
     * Cancel invitation sent to other user
     *
     * @param invite - invite sent to other player
     * @return - true if successfully cancelled, false otherwise
     */
    public boolean cancelInvitation(Invite invite) {
        return sentInvites.contains(invite) && invite.cancel();
    }

    /**
     * toString method for the User
     *
     * @return String representation of the user
     */
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
