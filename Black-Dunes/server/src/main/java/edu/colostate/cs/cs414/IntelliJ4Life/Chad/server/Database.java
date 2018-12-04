package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.NotificationsSession;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.sql.*;
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

    public Database() {
        this.user = null;
        this.auth = false;
    }

    public Database(User user) {
        this.user = user;
        this.auth = true;
    }

    public Database(String match, int limit) {
        this.match = match;
        this.limit = limit;
    }

    public void setUser(User user) {
        this.user = user;
        this.auth = true;
    }

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

    public void updateGameInDatabase(int gameID, String board, int turn) {
        String query = "UPDATE Games g SET Board = \'" + board + "\', Turn\n = " + turn +
                " WHERE g.GameID = " + gameID + ";";
        sendUpdateQueryToDatabase(query); // TODO do something with the return value (t/f)
    }

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

    public boolean addInviteToDatabase(int senderID, int inviteeID, String message) {
        if(!checkIfUserExistsInDatabaseByID(senderID) || !checkIfUserExistsInDatabaseByID(inviteeID)) {
            return false;
        }
        String query = "INSERT INTO Notifications (" +
                "User1ID, " +
                "User2ID, " +
                "Message" +
                ") VALUES (" +
                "'" + senderID + "', " +
                "'" + inviteeID + "', " +
                "'" + message + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

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
                ResultSet rsQuery = stQuery.executeQuery(query);
            ) {
                return parseNotificationsFromResultSet(rsQuery);
            }
        } catch(Exception e) {
            System.out.println(query);
            System.err.println("Encountered exception: " + e.getMessage());
        }
        return null;
    }

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

    public boolean deleteUserFromDatabase(int id) {
        String query = "DELETE FROM Users WHERE ID = " + id + ";";
        return sendUpdateQueryToDatabase(query);
    }

    public boolean setAllGamesFinishedByUserID(int id) {
        String query = "UPDATE Games SET Finished = 1 \n" +
                "WHERE User1ID = " + id + " OR User2ID = " + id + ";";
        sendUpdateQueryToDatabase(query);
        return false;
    }

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

    private boolean checkIfUserExistsInDatabaseByID(int id) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE ID = \'" + id + "\';";
        if(getCountAllFromDatabase(usernameQuery) > 0) {
            //System.err.println("User already exists");
            return true;
        }
        return false;
    }

    private boolean checkIfUserExistsInDatabase(String username) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username.toLowerCase() + "\';";
        if(getCountAllFromDatabase(usernameQuery) > 0) {
            //System.err.println("User already exists");
            return true;
        }
        return false;
    }

    private boolean checkIfUserExistsInDatabase(String username, String email) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username.toLowerCase() + "\';";
        String emailQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Email = \'" + email.toLowerCase() + "\';";
        if(getCountAllFromDatabase(usernameQuery) > 0 || getCountAllFromDatabase(emailQuery) > 0) {
            //System.err.println("User already exists");
            return true;
        }
        return false;
    }

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
        }
        else
            return null;
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificationRows;
    }

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

    private void authenticateUser(User user) {
        this.user = user;
        this.auth = true;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public boolean isAuth() {
        return auth;
    }

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

    public static void main(String[] args) {
        User u = new User(1, "sswensen", "swenyjr", "sswensen@email.com");
        Database db = new Database(u);
        Game g = db.getGameFromDatabaseByID(5);
        System.out.println(g);
    }

}
