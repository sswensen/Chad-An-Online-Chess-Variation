package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;

import java.util.ArrayList;

public class ActiveGames {
    ArrayList<Game> games = new ArrayList<Game>();

    public ArrayList getGames() {
        return games;
    }

    public void setGames(ArrayList games) {
        this.games = games;
    }

    public void add(Game g1) {
        boolean exists = false;
        for(Game g2 : games) {
            if(g1.getGameID() == g2.getGameID()) {
                exists = true;
            }
        }
        if(!exists) {
            games.add(g1);
        }
    }
}
