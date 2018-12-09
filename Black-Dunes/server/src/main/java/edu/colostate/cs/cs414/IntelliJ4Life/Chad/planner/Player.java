package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;


public class Player {
    private User user;
    private int userID;
    private Game game;
    private Color color;

    /**
     * Constructor for initializing a player
     *
     * @param color - color the player will be
     */
    public Player(Color color) {
        this.user = null;
        this.game = null;
        this.color = color;
    }

    /**
     * Constructor for initializing a player
     *
     * @param user - user the player corresponds to
     * @param game - gmae the player will be in
     * @param color - color the player will be
     */
    public Player(User user, Game game, Color color) {
        this.user = user;
        this.game = game;
        this.color = color;
        this.userID = user.getUserID();
    }

    /*******************
     * Accessors
     ******************/
    /**
     * Returns the user for the specific player
     *
     * @return - user for the spcific player
     */
    public User getUser() { return user; }

    /**
     * Returns the color for the player
     *
     * @return - color for the player
     */
    public Color getColor() { return color; }

    /*************
     * Public Methods
     *************/
    /**
     * Returns a piece for the specific player
     *
     * @param x - x position of the piece
     * @param y - y position of the piece
     * @return - the desired Piece
     */
    public Piece getPiece(int x, int y) {
        Piece ret = game.getBoard().getPiece(x, y);
        //Only returns a piece if it can move it
        if(ret == null)
            return null;
        if(ret.getColor().equals(this.color))
            return ret;
        else
            return null;
    }

    /**
     * makes the move for the player
     *
     * @param piece - piece to move
     * @param move - move to make
     * @return - true if move is successful, false otherwise
     */
    public boolean makeMove(Piece piece, int[] move) {
        return game.makeMove(this, piece, move);
    }
}