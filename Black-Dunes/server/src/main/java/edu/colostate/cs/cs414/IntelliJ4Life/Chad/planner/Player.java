package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;


public class Player {
    private int UserID;
    private boolean isTurn;
    private Color color;
    private Game game;


    public Player() {
        this.isTurn = false;
        this.color = null;
        this.game = null;
    }

    public Player(boolean isTurn, Color color, Game game) {
        this.isTurn = isTurn;
        this.color = color;
        this.game = game;
    }

    /*******************
     * Accessors
     ******************/
    public Boolean getTurn() { return isTurn; }

    public Color getColor() { return color; }

    public Game getGame() { return game; }

    /*************
     * Public Methods
     *************/

}