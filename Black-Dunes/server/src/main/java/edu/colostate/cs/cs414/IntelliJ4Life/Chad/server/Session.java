package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.util.ArrayList;

public class Session {
    public static void main(String[] args) {
        User u = new User(4, "sswensen", "swenyjr", "sswensen@email.com");
        Database db = new Database(u);
        ArrayList<Game> games = db.getGames();
    }
}
