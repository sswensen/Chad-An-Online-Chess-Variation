package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.time.LocalDateTime;
import java.util.Random;

public class Game {
    private LocalDateTime startTime;
    private String rules; // TODO this can be stored locally and does not need to be a variable
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private int turn; // 0 if white, 1 if black
    private int GameID;

    public Game() {
        startTime = LocalDateTime.now();
        board = new Board();
        setColors();
    }

    public Game(int GameID, String startTimeString, String board, int player1ID, int player2ID, int turn) {
        // Do we need to use the playerIDs?
        this.GameID = GameID;
        //this.startTime = LocalDateTime.parse(startTimeString); // TODO Fix this conversion
        this.board = new Board(board);
        if(turn == 1) {
            this. playerOne = new Player(true, Color.WHITE, this);
            this. playerOne = new Player(false, Color.BLACK, this);
            this.turn = 1;
        } else {
            this. playerOne = new Player(false, Color.WHITE, this);
            this. playerOne = new Player(true, Color.BLACK, this);
            this.turn = 2;
        }
    }

    /*******************
     * Accessors
     ******************/
    public LocalDateTime getGameDuration() {
        return startTime; // TODO this needs to be updated to minus current time from start time
    }

    public String getRules() {
        return "White begins. Players move, and must move, in turn.\n" +
                "\n" +
                "The King is confined to his 3x3 castle. He may go and capture using either the King's move or the Knight's move.\n" +
                "\n" +
                "It's customary to look at the King in terms of the squares it does not  cover. In the center it covers the whole castle, on the side he does not cover the square on the opposite side, and in the corner it does not cover the other corner squares.\n" +
                "\n" +
                "The rook moves like the rook in Chess, unhindered by castles and walls. If a rook ends its move inside the opponent's castle, it is promoted to Queen.\n" +
                "\n" +
                "The Queen moves like the Queen in Chess, unhindered by castles and walls.\n" +
                "\n" +
                "The mutual right of capture exists, and only exists, between an attacking piece on the wall and a defending piece inside the castle. Apart from this situation pieces simply block one another.";
    }

    public Board getBoard() {
        return board;
    }

    /*******************
     * Helpers
     ******************/
    private void setColors() {
        Random rand = new Random();
        if((rand.nextInt(50) + 1) >= 25) {
            playerOne = new Player(true, Color.WHITE, this);
            playerTwo = new Player(false, Color.BLACK, this);
        } else {
            playerTwo = new Player(false, Color.BLACK, this);
            playerOne = new Player(true, Color.WHITE, this);
        }
    }

}