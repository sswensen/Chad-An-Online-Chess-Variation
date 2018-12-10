package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public abstract class Piece {
    private int[] position;
    private boolean isTaken;
    private Color color;

    /*******************
     * Accessors
     ******************/
    /**
     * Gets the position for the piece
     *
     * @return - position for the piece
     */
    public int[] getPosition() {
        return this.position;
    }

    /**
     * Sets the position for the piece
     *
     * @param position
     */
    public void setPosition(int[] position) {
        this.position = position;
    }

    /**
     * Returns whether or not the piece is taken
     *
     * @return - true if piece is taken, false otherwise
     */
    public boolean isTaken() {
        return this.isTaken;
    }

    /**
     * Gets the color for the piece
     *
     * @return - the oolor for the piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color for the piece
     *
     * @param color - color to set for the piece
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Initializes the piece
     *
     * @param color - color to initialize the piece as
     */
    public void initializePiece(Color color) {
        this.color = color;
        this.isTaken = false;
        this.position = new int[]{-1, -1};
    }

    /**
     * initializes the piece with the given color and position
     *
     * @param color    - color to initialize the piece as
     * @param position - position for the new piece to be at
     */
    public void initializePiece(Color color, int[] position) {
        this.color = color;
        this.isTaken = false;
        this.position = position;
    }

    /*******************
     * Public Methods
     ******************/
    /**
     * Returns a list of valid moves in the form of locations the piece can move to
     *
     * @param board - 2D Piece array representing the board
     */
    abstract public ArrayList<int[]> validMoves(Piece[][] board);

    /**
     * Determines if the move is valid
     *
     * @param move  - move to make for the piece
     * @param board - 2D Piece array representing the board
     * @return - true if valid, false otherwise
     */
    // Find if a specific move is valid by checking if it exists in the validMoves list
    public boolean isValid(int[] move, Piece[][] board) {
        ArrayList<int[]> validMoves = this.validMoves(board);

        for(int i = 0; i < validMoves.size(); i++) {
            if(move[0] == validMoves.get(i)[0] && move[1] == validMoves.get(i)[1]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Moves the piece with the given move
     *
     * @param move  - move to make for the piece
     * @param board - 2D Piece array representing the board
     */
    public void move(int[] move, Piece[][] board) {
        // Sanity check -- validity should be determined in game class
        assert isValid(move, board) : "Invalid Move";

        // Update board
        board[getPosition()[0]][getPosition()[1]] = null;

        if(board[move[0]][move[1]] != null) {
            // Opponent piece occupies this position
            board[move[0]][move[1]].take();
        }

        // Update position
        this.position = move;

        // Check if BLACK piece needs to be upgraded to queen
        if(this instanceof Rook && this.getColor() == Color.BLACK && this.inCastle(Color.WHITE)) {
            board[move[0]][move[1]] = new Queen(this.getColor(), move);
        }
        // Check if WHITE piece needs to be upgraded to queen
        else if(this instanceof Rook && this.getColor() == Color.WHITE && this.inCastle(Color.BLACK)) {
            board[move[0]][move[1]] = new Queen(this.getColor(), move);
        } else {
            // No upgrade needed
            board[move[0]][move[1]] = this;
        }
    }

    /**
     * Removes the piece from the board due to capture
     */
    public void take() {
        this.isTaken = true;
        this.position = new int[]{-1, -1};
    }

    /**
     * Determine whether a specific move causes check
     *
     * @param move  - move that may cause check
     * @param color - color of the piece to move
     * @param board - 2D Piece array representing the board
     * @return - true if it causes check, false otherwise
     */
    public boolean causesCheck(int[] move, Color color, Piece[][] board) {
        King king = null;
        boolean found = false;

        // Find the location of this piece's color king on the board
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];

                if(piece instanceof King && piece.getColor() == color) {
                    king = (King) piece;
                    found = true;
                    break;
                }
            }

            if(found) {
                break;
            }
        }

        if(king == null) {
            // for testing cases where king isn't present
            return false;
        }

        // Make copy of board to make mock move
        Piece[][] mockBoard = new Piece[12][12];

        for(int i = 0; i < mockBoard.length; i++) {
            mockBoard[i] = board[i].clone();
        }

        // Make the move on the mock board
        mockBoard[getPosition()[0]][getPosition()[1]] = null;
        mockBoard[move[0]][move[1]] = this;

        // Save old position to be reset later
        int[] positionOld = this.getPosition();

        // Set position to position on mock board temporarily
        this.setPosition(new int[]{move[0], move[1]});

        // See if the move results in the king being in check
        if(king.inCheck(mockBoard)) {
            // Reset position and return true
            this.setPosition(positionOld);
            return true;
        }

        // King not in check. Reset position and return false
        this.setPosition(positionOld);
        return false;
    }

    /**
     * toString method for the piece
     *
     * @return - String represetation of the piece
     */
    // toString method
    public abstract String toString();

    /*******************
     * Helpers
     ******************/

    /**
     * Returns true or false depending on if the piece is on a caatle wall
     *
     * @param color - color wall to check if the piece is on
     * @return - true if piece is on a wall, false otherwise
     */
    private boolean onWall(Color color) {
        int[][] walls;

        if(color == Color.BLACK) {
            int[][] temp = {{1, 7}, {1, 8}, {1, 9},
                    {2, 6}, {3, 6}, {4, 6},
                    {2, 10}, {3, 10}, {4, 10},
                    {5, 7}, {5, 8}, {5, 9}};
            walls = temp;
        } else { // WHITE castle
            int[][] temp = {{6, 2}, {6, 3}, {6, 4},
                    {7, 1}, {8, 1}, {9, 1},
                    {7, 5}, {8, 5}, {9, 5},
                    {10, 2}, {10, 3}, {10, 4}};
            walls = temp;
        }

        for(int i = 0; i < walls.length; i++) {
            int[] location = walls[i];

            if((this.getPosition()[0] == location[0]) &&
                    (this.getPosition()[1] == location[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns whether or not the piece is in the castle
     *
     * @param color - color castle to check for
     * @return - true if in the castle, false otherwise
     */
    private boolean inCastle(Color color) {
        int[][] castleLocs;

        if(color == Color.BLACK) {
            int[][] temp = {{2, 7}, {2, 8}, {2, 9},
                    {3, 7}, {3, 8}, {3, 9},
                    {4, 7}, {4, 8}, {4, 9}};
            castleLocs = temp;
        } else { // WHITE castle
            int[][] temp = {{7, 2}, {7, 3}, {7, 4},
                    {8, 2}, {8, 3}, {8, 4},
                    {9, 2}, {9, 3}, {9, 4}};
            castleLocs = temp;
        }

        for(int i = 0; i < castleLocs.length; i++) {
            int[] location = castleLocs[i];

            if((this.getPosition()[0] == location[0]) &&
                    (this.getPosition()[1] == location[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds the move to the valid moves list
     *
     * @param move       - move to add to list
     * @param board      - 2D Piece array representing the board
     * @param validMoves - valid moves arrayList to add to
     * @return - true if successfully added, false otherwise
     */
    // Adds valid moves to the valid moves list and returns whether or not the calling function should keep adding moves
    protected boolean addMoveToList(int[] move, Piece[][] board, ArrayList<int[]> validMoves) {
        int row = move[0];
        int col = move[1];

        // Determines whether the calling function should continue adding moves
        boolean continueAdding = false;

        // Check if the space is empty
        if(board[row][col] == null) {
            // Check if making move results in check for the team's king
            if(!(causesCheck(move, this.getColor(), board))) {
                validMoves.add(move);
            }

            continueAdding = true;
        }
        // Check if the space is occupied by an opponent piece
        else if(board[row][col].getColor() != this.getColor()) {
            /*
             * Check whether Piece is capturable.
             * A Piece is capturable under 2 circumstances.
             *
             * 1. An attacking piece is on an opponents wall
             *    and is trying to capture a piece inside the opponent's
             *    castle
             *
             * 2. A defending piece is inside it's own castle and
             *    is trying to capture an enemy piece on the wall of
             *    its castle
             */

            // boolean determining if the move causes check
            boolean causesCheck = causesCheck(move, this.getColor(), board);

            // Check if piece is capturable for BLACK attacker
            if(this.getColor() == Color.BLACK && this.onWall(Color.WHITE) &&
                    board[row][col].inCastle(Color.WHITE)) {
                // BLACK piece attacking WHITE castle
                if(!causesCheck) {
                    validMoves.add(move);
                }
            }

            // Check if piece is capturable for WHITE attacker
            else if(this.getColor() == Color.WHITE && this.onWall(Color.BLACK) &&
                    board[row][col].inCastle(Color.BLACK)) {
                // WHITE piece attacking BLACK castle
                if(!causesCheck) {
                    validMoves.add(move);
                }
            }

            // Check if piece is capturable for BLACK defender
            if(this.getColor() == Color.BLACK && this.inCastle(Color.BLACK) &&
                    board[row][col].onWall(Color.BLACK)) {
                // BLACK piece defending BLACK castle
                if(!causesCheck) {
                    validMoves.add(move);
                }
            }

            // Check if piece is capturable for WHITE defender
            else if(this.getColor() == Color.WHITE && this.inCastle(Color.WHITE) &&
                    board[row][col].onWall(Color.WHITE)) {
                // WHITE piece defending WHITE castle
                if(!causesCheck) {
                    validMoves.add(move);
                }
            }
        }

        return continueAdding;
    }
}