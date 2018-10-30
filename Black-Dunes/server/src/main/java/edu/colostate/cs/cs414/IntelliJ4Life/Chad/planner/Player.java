package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;


public class Player {
    private User user;
    private int UserID;
    private Game game;
    private Color color;

    public Player(Color color) {
        this.user = null;
        this.game = null;
        this.color = color;
    }

    public Player(User user, Game game, Color color) {
        this.user = user;
        this.game = game;
        this.color = color;
    }

    /*******************
     * Accessors
     ******************/
    public User getUser() { return user; }

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