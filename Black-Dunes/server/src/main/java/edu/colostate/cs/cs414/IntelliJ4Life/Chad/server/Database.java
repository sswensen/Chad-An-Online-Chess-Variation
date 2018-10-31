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
import spark.Request;

public class Database {

    private int version;
    private String type;
    private String match;
    private int ID;
    private int limit;
    private ArrayList<Game> games;

    private final static String myDriver = "com.mysql.jdbc.Driver";
    private final static String user = "cs414";
    private final static String pass = "Intellij4life!";

    private final static String count = "";
    private final static String search = "";

    public Database(int userID) {
        this.ID = userID;
    }

    public Database(String match, int limit) {
        this.match = match;
        this.limit = limit;
    }

    //Both findResults and printJSON came from the slides as a template to start

//    private void getCurrentGamesFromDatabase() {
//        String query = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn\n" +
//                "FROM Games g, Users u\n" +
//                "WHERE g.User1ID = " + ID + " AND u.ID = " + ID + ";";
//        String dbUrl;
//
//        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
//        try {
//            Class.forName(myDriver);
//            try(Connection conn = DriverManager.getConnection(dbUrl, user, pass);
//                Statement stQuery = conn.createStatement();
//                ResultSet rsQuery = stQuery.executeQuery(query)
//            ) {
//                System.out.println("Query: " + query);
//                this.games = parseGamesFromResultSet(rsQuery);
//            }
//        } catch(Exception e) {
//            System.err.println("Encountered exception: " + e.getMessage());
//        }
//    }

    private void updateGameInDatabase(int gameID, String board, int turn) {
        String query = "UPDATE Games g SET Board = \'" + board + "\', Turn\n = " + turn +
                " WHERE g.GameID = " + gameID + ";";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);
            Statement stQuery = conn.createStatement();
            System.out.println("Query: " + query);

            stQuery.executeUpdate(query);

            // this.games = updateGames(rsQuery);
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
    }

    private ArrayList<Game> parseGamesFromResultSet(ResultSet query) throws SQLException {
        ArrayList<Game> out = new ArrayList<Game>();
        while(query.next()) {
            Integer gameID = Integer.parseInt(query.getString("GameID"));
            String time = query.getString("StartTime");
            String board = query.getString("Board");
            Integer player1 = Integer.parseInt(query.getString("User1ID"));
            Integer player2 = Integer.parseInt(query.getString("User2ID"));
            Integer turn = Integer.parseInt(query.getString("Turn"));

            out.add(new Game(gameID, time, board, player1, player2, turn)); // TODO fill here
        }
        return out;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public static void main(String[] args) {
        Database db = new Database(1);
        db.getCurrentGamesFromDatabase();
        Game g = db.getGames().get(0);
        // g.setBoard(new Board(""));
        db.updateGameInDatabase(g.getGameID(), "0,0,1,0 2,8,1,0 2,9,1,0 3,7,1,0 3,8,3,0 3,9,1,0 4,7,1,0 4,8,1,0 4,9,1,0 7,2,1,1 7,3,1,1 7,4,1,1 8,2,1,1 8,3,3,1 8,4,1,1 9,2,1,1 9,3,1,1 9,4,1,1", 2);
        System.out.println("PAUSE");
    }

}
