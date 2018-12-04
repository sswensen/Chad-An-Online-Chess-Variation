package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;


public class Player {
    private User user;
    private int userID;
    private Game game;
    private Color color;

    /**
     * Object that represents a Player
     * @param color
     */
    public Player(Color color) {
        this.user = null;
        this.game = null;
        this.color = color;
    }

    public Player(User user, Game game, Color color) {
        this.user = user;
        this.game = game;
        this.color = color;
        this.userID = user.getUserID();
    }

    /**
     * Accessor to get user object
     * @return
     */
    public User getUser() { return user; }

    /**
     * Accessor that gets the color of the player
     * @return
     */
    public Color getColor() { return color; }

    /**
     * Method that gets a piece object from specific coordinate
     * @param x X cord of piece to be retrieved
     * @param y Y cord of piece to be retrieved
     * @return
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
     * Method that moves a piece
     * @param piece Piece to be moved
     * @param move valid move that will be used to move piece
     * @return
     */
    public boolean makeMove(Piece piece, int[] move) {
        return game.makeMove(this, piece, move);
    }


}