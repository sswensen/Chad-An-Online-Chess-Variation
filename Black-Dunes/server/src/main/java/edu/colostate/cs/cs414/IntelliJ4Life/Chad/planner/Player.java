package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;


public class Player {
    private int userID;
    private Game game;
    private Color color;

    public Player(Color color) {
        this.userID = -1;
        this.game = null;
        this.color = color;
    }

    public Player(int userID, Game game, Color color) {
        this.userID = userID;
        this.game = game;
        this.color = color;
    }

    /*******************
     * Accessors
     ******************/
    public int getUserID() { return userID; }

    public Color getColor() { return color; }

    /*************
     * Public Methods
     *************/
    public Piece getPiece(int x, int y) {
        Piece ret = game.getBoard().getPiece(x, y);
        //Only returns a piece if it can move it
        if(ret.getColor().equals(this.color))
            return ret;
        else
            return null;
    }

    public boolean makeMove(Piece piece, int[] move) {
        return game.makeMove(this, piece, move);
    }


}