package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;

import java.util.ArrayList;

public class ActiveGames {
    ArrayList<Game> games = new ArrayList<Game>();

    /**
     * Returns the active games for the player
     *
     * @return - ArrayList containing the active games
     */
    public ArrayList getGames() {
        return games;
    }

    /**
     * Sets the active games list with the given games
     *
     * @param games - games to set as active games
     */
    public void setGames(ArrayList games) {
        this.games = games;
    }

    /**
     * Adds a game to the active games list
     *
     * @param g1 - game to add
     */
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

    /**
     * Returns all the games a specific user is playing in
     *
     * @param userID - userID to find games for
     * @return - ArrayList containing the user's games
     */
    public ArrayList<Game> getGamesFromUserID(String userID) {
        ArrayList<Game> returnGames = new ArrayList<>();
        int id = Integer.parseInt(userID);
        for(Game g : games) {
            if(g.getPlayerOne().getUser().getUserID() == id || g.getPlayerTwo().getUser().getUserID() == id) {
                returnGames.add(g);
            }
        }
        return returnGames;
    }

    /**
     * gets the game by the game ID
     *
     * @param gameID - gameID used to get game
     * @return - Game
     */
    public Game getGameFromGameID(String gameID) {
        for(Game g : games) {
            if(g.getGameID() == (Integer.parseInt(gameID))) {
                return g;
            }
        }
        return null;
    }
}
