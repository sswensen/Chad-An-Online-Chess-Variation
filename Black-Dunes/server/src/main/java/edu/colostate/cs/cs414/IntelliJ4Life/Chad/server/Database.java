package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;
import spark.Request;

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

    //Both findResults and printJSON came from the slides as a template to start

    public void getCurrentGamesFromDatabase() {
        String query = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn\n" +
                "FROM Games g, Users u\n" +
                "WHERE (g.User1ID = " + user.getUserID() + " OR g.User2ID = " + user.getUserID() + ") AND u.ID = " + user.getUserID() + ";";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                System.out.println("Query: " + query);
                this.games = parseGamesFromResultSet(rsQuery);
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
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
                "'" + username + "', " +
                "'" + nickname + "', " +
                "'" + email + "', " +
                "'" + password + "'" +
                ");\n";
        return sendUpdateQueryToDatabase(query);
    }

    public User getUserFromDatabase(String username, String password) {
        if(checkIfUserExistsInDatabase(username)) {
            String query = "SELECT ID, Nickname, Email " +
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
                    System.out.println("Query: " + query);
                    ArrayList<User> userList = parseUsersFromResultSet(rsQuery);
                    if(userList.size() == 1) {
                        user = userList.get(0);
                        auth = true;
                        return userList.get(0);
                    } else if(userList.size() == 0){
                        System.err.println("Username/password incorrect");
                    } else {
                        System.err.println("More than one users with same username and password");
                    }
                }
            } catch(Exception e) {
                System.err.println("Encountered exception: " + e.getMessage());
            }
        }
        return null;
    }

    private boolean checkIfUserExistsInDatabase(String username) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username + "\';";
        if(getCountAllFromDatabase(usernameQuery) > 0) {
            //System.err.println("User already exists");
            return true;
        }
        return false;
    }

    private boolean checkIfUserExistsInDatabase(String username, String email) {
        String usernameQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Username = \'" + username + "\';";
        String emailQuery = "SELECT COUNT(*) AS Count FROM Users WHERE Email = \'" + email + "\';";
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
            int player1 = Integer.parseInt(rs.getString("User1ID"));
            int player2 = Integer.parseInt(rs.getString("User2ID"));
            int turn = Integer.parseInt(rs.getString("Turn"));

            out.add(new Game(gameID, time, board, player1, player2, turn)); // TODO fill here
        }
        return out;
    }

    private ArrayList<User> parseUsersFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<User> out = new ArrayList<User>();
        while(rs.next()) {
            int userID = Integer.parseInt(rs.getString("ID"));
            String nickName = rs.getString("Nickname");
            String email = rs.getString("Email");

            out.add(new User(userID, nickName, email));
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
            System.out.println("Query: " + query);
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
                System.out.println("Query: " + query);
                rsQuery.next(); // This is needed to do a get string on the rsQuery because the iterator index is at -1 to start
                return Integer.parseInt(rsQuery.getString("Count"));
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }

        return -1;
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

    public static void main(String[] args) {
        Database db = new Database();
//        db.getCurrentGamesFromDatabase();
//        Game g = db.getGames().get(0);
//        // g.setBoard(new Board(""));
//        db.updateGameInDatabase(g.getGameID(), "0,0,1,0 2,8,1,0 2,9,1,0 3,7,1,0 3,8,3,0 3,9,1,0 4,7,1,0 4,8,1,0 4,9,1,0 7,2,1,1 7,3,1,1 7,4,1,1 8,2,1,1 8,3,3,1 8,4,1,1 9,2,1,1 9,3,1,1 9,4,1,1", 1);
//        System.out.println("PAUSE");

        //Boolean b = db.registerUserInDatabase("sswensen", "swenyjr", "sswensen@email.com", "mypassword");
        //System.out.println(b);

        //User u = db.getUserFromDatabase("sswensen", "mypassword");
        User u = db.getUserFromDatabase("sswensen", "test");
        u.toString();
    }

}
