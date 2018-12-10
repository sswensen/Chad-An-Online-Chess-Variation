package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database {

    private int version;
    private String type;
    private String match;
    private User user;
    private int limit;
    private ArrayList<Game> games;
    private boolean auth = false;

    private final static String myDriver = "com.mysql.jdbc.Driver";
    private final static String dbusername = "cs414";
    private final static String dbpass = "Intellij4life!";

    private final static String count = "";
    private final static String search = "";

    /**
     * Constructor to intialize the database
     */
    public Database() {
        this.user = null;
        this.auth = false;
    }

    /**
     * Constructor to intialize the database with a user
     *
     * @param user - User for the database
     */
    public Database(User user) {
        this.user = user;
        this.auth = true;
    }

    /**
     * Constructor to intialize the database with a match and a limit
     *
     * @param match - String used for matching
     * @param limit - limit for the database
     */
    public Database(String match, int limit) {
        this.match = match;
        this.limit = limit;
    }

    /**
     * Sets user for database
     *
     * @param user - User to set
     */
    public void setUser(User user) {
        this.user = user;
        this.auth = true;
    }

    /**
     * Gets the current games from the database
     */
    public void getCurrentGamesFromDatabase() {
        String q1 = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn, Finished\n" +
                "FROM Games g, Users u\n" +
                "WHERE (g.User1ID = " + user.getUserID() + " OR g.User2ID = " + user.getUserID() + ") AND u.ID = " + user.getUserID() + ";";
        String query = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn, Finished, u1.ID, u1.Username,  u1.Nickname, u1.Email, u2.ID, u2.Username,  u2.Nickname, u2.Email\n" +
                "FROM Games g\n" +
                "LEFT JOIN Users u1 ON u1.ID = g.User1ID\n" +
                "LEFT JOIN Users u2 ON u2.ID = g.User2ID\n" +
                "WHERE (g.User1ID = " + user.getUserID() + " OR g.User2ID = " + user.getUserID() + ") AND (u1.ID = " + user.getUserID() + " OR u2.ID = + " + user.getUserID() + ");";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                this.games = parseGamesFromResultSet(rsQuery);
            }
        } catch(Exception e) {
            System.out.println(query);
            System.err.println("Encountered exception: " + e.getMessage());
        }
    }

    /**
     * Get a game from the database by game ID
     *
     * @param gameID - gameID used to get game from database
     * @return - Game retrieved from database
     */
    public Game getGameFromDatabaseByID(int gameID) {
        String query = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn, Finished\n" +
                "FROM Games g, Users u\n" +
                "WHERE GameID = " + gameID + ";";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                return parseGameFromResultSet(rsQuery);
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update a game in the database
     *
     * @param gameID - Game ID to update
     * @param board  - new updated board for the game
     * @param turn   - new player's turn for the game
     */
    public void updateGameInDatabase(int gameID, String board, int turn) {
        String query = "UPDATE Games g SET Board = \'" + board + "\', Turn\n = " + turn +
                " WHERE g.GameID = " + gameID + ";";
        sendUpdateQueryToDatabase(query); // TODO do something with the return value (t/f)
    }

    /**
     * add game to the database
     *
     * @param user1ID   - User ID for first user
     * @param user2ID   - user ID for second user
     * @param startTime - start time for the game
     * @param turn      - turn for the game
     * @param board     - board for the game
     * @return - true if successfull, false otherwise
     */
    public boolean addGameToDatabase(int user1ID, int user2ID, LocalDateTime startTime, int turn, String board) {
        if(!checkIfUserExistsInDatabaseByID(user1ID) || !checkIfUserExistsInDatabaseByID(user2ID)) {
            return false;
        }
        String query = "INSERT INTO Games (" +
                "User1ID, " +
                "User2ID, " +
                "StartTime, " +
                "Turn, " +
                "Board" +
                ") VALUES (" +
                "'" + user1ID + "', " +
                "'" + user2ID + "', " +
                "'" + startTime + "', " +
                "'" + turn + "', " +
                "'" + board + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * register user into database
     *
     * @param username - username for the user
     * @param nickname - nickname for the user
     * @param email    - email for the useer
     * @param password - password for the user
     * @return - true if successful, false otherwise
     */
    public boolean registerUserInDatabase(String username, String nickname, String email, String password) {
        if(checkIfUserExistsInDatabase(username, email)) {
            // TODO caller needs to prompt again for new email/username
            return false;
        }
        String query = "INSERT INTO Users (" +
                "Username, " +
                "Nickname, " +
                "Email, " +
                "Pass" +
                ") VALUES (" +
                "'" + username.toLowerCase() + "', " +
                "'" + nickname + "', " +
                "'" + email.toLowerCase() + "', " +
                "'" + password + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * Gets the highest group ID number
     *
     * @return - gets the highest gourp ID
     */
    public int getMaxInviteGroupIdFromDatabase() {
        String query = "SELECT MAX(GroupID) FROM Notifications;";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement(); // TODO make these global and test that connections stays live
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                //System.out.println("Query: " + query);
                int maxGroupId = -1;

                while(rsQuery.next()) {
                    maxGroupId = Integer.parseInt(rsQuery.getString("MAX(GroupID)"));
                }

                if(maxGroupId != -1) {
                    return maxGroupId;
                } else {
                    System.err.println("No group ID's in database");
                }
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * adds an invite to the database
     *
     * @param senderID  - sender ID for the invite
     * @param inviteeID - invite ID for the invite
     * @param message   - messgage for the invite
     * @param groupID   - group ID for the invite
     * @return - true if successful, false otherwise
     */
    public boolean addInviteToDatabase(int senderID, int inviteeID, String message, int groupID) {
        if(!checkIfUserExistsInDatabaseByID(senderID) || !checkIfUserExistsInDatabaseByID(inviteeID)) {
            return false;
        }
        String query = "INSERT INTO Notifications (" +
                "User1ID, " +
                "User2ID, " +
                "Message, " +
                "GroupID" +
                ") VALUES (" +
                "'" + senderID + "', " +
                "'" + inviteeID + "', " +
                "'" + message + "', " +
                "'" + groupID + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * Adds notification to the database
     *
     * @param userID  - User ID for notification
     * @param message - message for notification
     * @return - true if successful, false otherwisee
     */
    public boolean addNotificationToDatabase(int userID, String message) {
        if(!checkIfUserExistsInDatabaseByID(userID)) {
            return false;
        }
        String query = "INSERT INTO Notifications (" +
                "User1ID, " +
                "Message" +
                ") VALUES (" +
                "'" + userID + "', " +
                "'" + message + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * Gets all the notifications for the specified user
     *
     * @param userID - userID used to get the notifications
     * @return - ArrayList of notifications
     */
    public ArrayList<NotificationRow> getNotificationsFromDatabase(int userID) {
        if(!checkIfUserExistsInDatabaseByID(userID)) {
            return null;
        }
        String query = "SELECT * FROM Notifications " +
                "WHERE (User1ID = '" + userID + "') OR (User2ID = '" + userID + "');\n";
        String dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                return parseNotificationsFromResultSet(rsQuery);
            }
        } catch(Exception e) {
            System.out.println(query);
            System.err.println("Encountered exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the notification from the database by InviteID
     *
     * @param inviteID - Invite ID to get
     * @return - returns desired notification
     */
    public NotificationRow getNotificationFromDatabaseByInviteID(int inviteID) {
        if(!checkIfNotificationExistsInDatabaseByID(inviteID)) {
            return null;
        }
        String query = "SELECT * FROM Notifications " +
                "WHERE (ID = '" + inviteID + "');\n";
        String dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                return parseNotificationsFromResultSet(rsQuery).get(0);
            }
        } catch(Exception e) {
            System.out.println(query);
            System.err.println("Encountered exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a notification from database by the invite ID
     *
     * @param inviteID - Invite ID to delete
     * @return - true if successful, false otherwise
     */
    public boolean deleteNotificationRowFromDatabaseByInvitationID(int inviteID) {
        if(!checkIfNotificationExistsInDatabaseByID(inviteID)) {
            return false;
        }

        String query = "DELETE FROM Notifications WHERE (ID = '" + inviteID + "');";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * Checks if a notification is in the database
     *
     * @param id - ID of notification to check
     * @return - true if the notification is in the database, false otherwise
     */
    private boolean checkIfNotificationExistsInDatabaseByID(int id) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Notifications WHERE ID = \'" + id + "\';";
        //System.err.println("User already exists");
        return getCountAllFromDatabase(usernameQuery) > 0;
    }

    /**
     * Gets all the users from the database
     *
     * @return - ArrayList of all the users in the database
     */
    public ArrayList<User> getAllUsersFromDatabase() {

        String query = "SELECT ID, Username, Nickname, Email " +
                "FROM Users;";
        String dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                ArrayList<User> userList = parseUsersFromResultSet(rsQuery);
                return userList;
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
        // If no user found with supplied username and password, return a user with -1 as ID
        return null;
    }

    /**
     * Gets a user from the database by the username and password
     *
     * @param username - username of user to get
     * @param password - password of user to get
     * @return
     */
    public User getUserFromDatabase(String username, String password) {
        username = username.toLowerCase();
        password = password.toLowerCase();
        if(checkIfUserExistsInDatabase(username)) {
            String query = "SELECT ID, Username, Nickname, Email " +
                    "FROM Users " +
                    "WHERE Username = \'" + username + "\' " +
                    "AND Pass = \'" + password + "\';";
            String dbUrl;

            dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
            try {
                Class.forName(myDriver);
                try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                    Statement stQuery = conn.createStatement(); // TODO make these global and test that connections stays live
                    ResultSet rsQuery = stQuery.executeQuery(query)
                ) {
                    //System.out.println("Query: " + query);
                    ArrayList<User> userList = parseUsersFromResultSet(rsQuery);
                    if(userList.size() == 1) {
                        authenticateUser(userList.get(0));
                        return userList.get(0);
                    } else if(userList.size() == 0) {
                        System.err.println("Username/password incorrect");
                    } else {
                        System.err.println("More than one users with same username and password");
                    }
                }
            } catch(Exception e) {
                System.err.println("Encountered exception: " + e.getMessage());
            }
        }
        // If no user found with supplied username and password, return a user with -1 as ID
        return new User(-1, "", "");
    }

    /**
     * Deletss user from the database by the user ID
     *
     * @param id - ID of user to delete from database
     * @return - true if successful, false otherwise
     */
    public boolean deleteUserFromDatabase(int id) {
        String query = "DELETE FROM Users WHERE ID = " + id + ";";
        return sendUpdateQueryToDatabase(query);
    }

    /**
     * Sets all the games for a specific user to finished
     *
     * @param id - ID used to get games for the user
     * @return - true if successful, false otherwise
     */
    public boolean setAllGamesFinishedByUserID(int id) {
        String query = "UPDATE Games SET Finished = 1 \n" +
                "WHERE User1ID = " + id + " OR User2ID = " + id + ";";
        sendUpdateQueryToDatabase(query);
        return false;
    }

    /**
     * Gets a user from the database by the user ID
     *
     * @param id - User ID for the desired user
     * @return - User object of desired user
     */
    public User getUserFromDatabaseByID(int id) {
        if(checkIfUserExistsInDatabaseByID(id)) {
            String query = "SELECT ID, Username, Nickname, Email " +
                    "FROM Users " +
                    "WHERE ID = \'" + id + "\';";
            String dbUrl;

            dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
            try {
                Class.forName(myDriver);
                try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                    Statement stQuery = conn.createStatement(); // TODO make these global and test that connections stays live
                    ResultSet rsQuery = stQuery.executeQuery(query)
                ) {
                    //System.out.println("Query: " + query);
                    ArrayList<User> userList = parseUsersFromResultSet(rsQuery);
                    if(userList.size() == 1) {
                        //user = userList.get(0);
                        //auth = true;
                        return userList.get(0);
                    } else if(userList.size() == 0) {
                        System.err.println("No users found");
                    } else {
                        System.err.println("More than one users with same ID");
                    }
                }
            } catch(Exception e) {
                System.err.println("Encountered exception: " + e.getMessage());
            }
        }
        return new User(-1, "", "");
    }

    /**
     * Checks if specified user is in the database by user ID
     *
     * @param id - User ID to check is in database
     * @return - true if in database, false otherwise
     */
    private boolean checkIfUserExistsInDatabaseByID(int id) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE ID = \'" + id + "\';";
        //System.err.println("User already exists");
        return getCountAllFromDatabase(usernameQuery) > 0;
    }

    /**
     * Checks if specified user is in the database by the username
     *
     * @param username - username to check is in database
     * @return - true if in database, false otherwise
     */
    private boolean checkIfUserExistsInDatabase(String username) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username.toLowerCase() + "\';";
        //System.err.println("User already exists");
        return getCountAllFromDatabase(usernameQuery) > 0;
    }

    /**
     * Checks if specified user is in the database by the username
     *
     * @param username - username to check is in database
     * @param email    - email to check is in database
     * @return - true if in database, false otherwise
     */
    private boolean checkIfUserExistsInDatabase(String username, String email) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username.toLowerCase() + "\';";
        String emailQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Email = \'" + email.toLowerCase() + "\';";
        //System.err.println("User already exists");
        return getCountAllFromDatabase(usernameQuery) > 0 || getCountAllFromDatabase(emailQuery) > 0;
    }

    /**
     * Returns the parsed Game objects from the result of the SQL query
     *
     * @param rs - result set from the database
     * @return - ArrayList of games to return
     * @throws SQLException
     */
    private ArrayList<Game> parseGamesFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Game> out = new ArrayList<Game>();
        while(rs.next()) {
            int gameID = Integer.parseInt(rs.getString("GameID"));
            String time = rs.getString("StartTime");
            String board = rs.getString("Board");
            int turn = Integer.parseInt(rs.getString("Turn"));
            int finished = Integer.parseInt(rs.getString("Finished"));


            int user1ID = Integer.parseInt(rs.getString("u1.ID"));
            int user2ID = Integer.parseInt(rs.getString("u2.ID"));


            String user1Username = rs.getString("u1.Username");
            String user2Username = rs.getString("u2.Username");

            String user1Nickname = rs.getString("u1.Nickname");
            String user2Nickname = rs.getString("u2.Nickname");

            String user1Email = rs.getString("u1.Email");
            String user2Email = rs.getString("u2.Email");

            User u1 = new User(user1ID, user1Username, user1Nickname, user1Email);
            User u2 = new User(user2ID, user2Username, user2Nickname, user2Email);


            out.add(new Game(gameID, time, board, u1, u2, turn, finished)); // TODO fill here
        }
        return out;
    }

    /**
     * Returns the parsed Game object from the result of the SQL query
     *
     * @param rs - result set from the database
     * @return - Games object to return
     * @throws SQLException
     */
    private Game parseGameFromResultSet(ResultSet rs) throws SQLException {
        if(rs.next()) {
            int gameID = Integer.parseInt(rs.getString("GameID"));
            String startTime = rs.getString("StartTime");
            String board = rs.getString("Board");
            int turn = Integer.parseInt(rs.getString("Turn"));
            int finished = Integer.parseInt(rs.getString("Finished"));

            User u1 = getUserFromDatabaseByID(Integer.parseInt(rs.getString("User1ID")));
            User u2 = getUserFromDatabaseByID(Integer.parseInt(rs.getString("User2ID")));


            return new Game(gameID, startTime, board, u1, u2, turn, finished);
        } else
            return null;
    }

    /**
     * Returns the parsed Notification objects from the result of the SQL query
     *
     * @param rs - result set from the database
     * @return - ArrayList of Notifications to return
     */
    private ArrayList<NotificationRow> parseNotificationsFromResultSet(ResultSet rs) {
        ArrayList<NotificationRow> notificationRows = new ArrayList<>();
        try {
            while(rs.next()) {
                int notificationID = Integer.parseInt(rs.getString("ID"));
                int user1ID = Integer.parseInt(rs.getString("User1ID"));
                int user2ID = Integer.parseInt(rs.getString("User2ID"));
                String message = rs.getString("Message");
                notificationRows.add(new NotificationRow(notificationID, user1ID, user2ID, message));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return notificationRows;
    }

    /**
     * Returns the parsed User objects from the result of the SQL query
     *
     * @param rs - result set from the database
     * @return - ArrayList of users to return
     * @throws SQLException
     */
    private ArrayList<User> parseUsersFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<User> out = new ArrayList<User>();
        while(rs.next()) {
            int userID = Integer.parseInt(rs.getString("ID"));
            String username = rs.getString("Username");
            String nickName = rs.getString("Nickname");
            String email = rs.getString("Email");

            out.add(new User(userID, username, nickName, email));
        }
        return out;
    }

    /**
     * @param query - query to send to the database
     * @return - true if successful, false otherwise
     */
    private boolean sendUpdateQueryToDatabase(String query) {
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
            Statement stQuery = conn.createStatement();
            //System.out.println("Query: " + query);
            int ret = stQuery.executeUpdate(query);
            if(ret > -1) {
                return true;
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Gets the total number of entries in the database
     *
     * @param query - query to send to the database
     * @return - total number of entries in the database
     */
    private int getCountAllFromDatabase(String query) {
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement(); // TODO make these global and test that connections stays live
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                //System.out.println("Query: " + query);
                rsQuery.next(); // This is needed to do a get string on the rsQuery because the iterator index is at -1 to start
                return Integer.parseInt(rsQuery.getString("Count"));
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Authenticates user by setting auth variable to true
     *
     * @param user - user to authenticate
     */
    private void authenticateUser(User user) {
        this.user = user;
        this.auth = true;
    }

    /**
     * Gets all the games from the database
     *
     * @return - Games in the database
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * Returns the authorization
     *
     * @return - ture if authorized, false otherwise
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Sets the authorization
     *
     * @param auth - authorization to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public class NotificationRow {
        public int notificationID;
        public int user1ID;
        public int user2ID;
        public String message;

        public NotificationRow(int notificationID, int user1ID, int user2ID, String message) {
            this.notificationID = notificationID;
            this.user1ID = user1ID;
            this.user2ID = user2ID;
            this.message = message;
        }
    }

    /**
     * Main method for testing purposes
     *
     * @param args - program arguments, null for this main method
     */
    public static void main(String[] args) {
        User u = new User(1, "sswensen", "swenyjr", "sswensen@email.com");
        Database db = new Database(u);
        Game g = db.getGameFromDatabaseByID(5);
        System.out.println(g);
    }

}
