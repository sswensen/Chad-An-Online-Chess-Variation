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
    private int limit;
    private ArrayList<Game> games;

    private final static String myDriver = "com.mysql.jdbc.Driver";
    private final static String user = "cs414";
    private final static String pass = "Intellij4life!";

    private final static String count = "";
    private final static String search = "";

    public Database() {
    }

    public Database(String match, int limit) {
        this.match = match;
        this.limit = limit;
    }

    //Both findResults and printJSON came from the slides as a template to start

    private void makeQuery() {
        String query = "SELECT GameID, StartTime, Board, User1ID, User2ID, Turn\n" +
                "FROM Games g, Users u\n" +
                "WHERE g.User1ID = u.ID;";
        String dbUrl;

        dbUrl = "jdbc:mysql://cs414.db.10202520.4f5.hostedresource.net/cs414";
        try {
            Class.forName(myDriver);
            try(Connection conn = DriverManager.getConnection(dbUrl, user, pass);
                Statement stQuery = conn.createStatement();
                ResultSet rsQuery = stQuery.executeQuery(query)
            ) {
                this.games = getGames(rsQuery);
            }
        } catch(Exception e) {
            System.err.println("Encountered exception: " + e.getMessage());
        }
    }

    private ArrayList<Game> getGames(ResultSet query) throws SQLException {
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

    private void saveGames() throws SQLException {
    }

    public static void main(String[] args) {
        Database db = new Database();
        db.makeQuery();
        System.out.println("PAUSE");
    }

}
