package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public abstract class Piece {
    private int[] position;
    private boolean isTaken;
    private Color color;

    /*******************
     * Accessors
     ******************/
    public int[] getPosition() {
        return this.position;
    }
    
    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean isTaken() {
        return this.isTaken;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void initializePiece(Color color) {
        this.color = color;
        this.isTaken = false;
        this.position = new int[] {-1, -1};
    }

    /*******************
     * Public Methods
     ******************/
    // Returns a list of valid moves in the form of locations the piece can move to
    abstract public ArrayList<int[]> validMoves(Piece[][] board);

    // Conducts the passed in move
    public void move(int[] move, Piece[][] board) {
        if (this.validMoves(board).contains(move)) {
            // Update board
            board[getPosition()[0]][getPosition()[1]] = null;
            board[move[0]][move[1]] = this;

            // Update position
            this.position = move;
        }
        else {
            // @TODO: Handle invalid move
        }
    }

    // Removes the piece from the board due to capture
    public void take() {
        this.isTaken = true;
        int[] removedPosition = {-1, -1};
        this.position = removedPosition;
    }

    /*******************
     * Helpers
     ******************/
    // Returns true or false depending on if the piece is on a caatle wall
    private boolean onWall(Color color){
        int[][] walls;

        if (color == Color.BLACK) {
            int [][] temp = {{1, 7}, {1, 8}, {1, 9},
                             {2, 6}, {3, 6}, {4, 6},
                             {2, 10}, {3, 10}, {4, 10},
                             {5, 7}, {5, 8}, {5, 9}};
            walls = temp;
        }
        else { // WHITE castle
            int [][] temp = {{6, 2}, {6, 3}, {6, 4},
                             {7, 1}, {8, 1}, {9, 1},
                             {7, 5}, {8, 5}, {9, 5},
                             {10, 2}, {10, 3}, {10, 4}};
            walls = temp;
        }

        for (int i = 0; i < walls.length; i++) {
            int[] location = walls[i];

            if ((this.getPosition()[0] == location[0]) &&
                (this.getPosition()[1] == location[1])) {
                return true;
            }
        }

        return false;
    }

    private boolean inCastle(Color color) {
        int[][] castleLocs;

        if (color == Color.BLACK) {
            int [][] temp = {{2, 7}, {2, 8}, {2, 9},
                             {3, 7}, {3, 8}, {3, 9},
                             {4, 7}, {4, 8}, {4, 9}};
            castleLocs = temp;
        }
        else { // WHITE castle
            int [][] temp = {{7, 2}, {7, 3}, {7, 4},
                             {8, 2}, {8, 3}, {8, 4},
                             {9, 2}, {9, 3}, {9, 4}};
            castleLocs = temp;
        }

        for (int i = 0; i < castleLocs.length; i++) {
            int[] location = castleLocs[i];

            if ((this.getPosition()[0] == location[0]) &&
                    (this.getPosition()[1] == location[1])) {
                return true;
            }
        }

        return false;
    }

    // Adds valid moves to the valid moves list and returns whether or not the calling function should keep adding moves
    protected boolean addMoveToList(int[] move, Piece[][] board, ArrayList<int[]> validMoves){
        int row = move[0];
        int col = move[1];

        if (row == 8 && col == 2) {
            int i = 11;
        }

        // Determines whether the calling function should continue adding moves
        boolean continueAdding = false;

        // Check if the space is empty
        if (board[row][col] == null) {
            validMoves.add(move);
            continueAdding = true;
        }
        // Check if the space is occupied by an opponent piece
        else if (board[row][col].getColor() != this.getColor()) {
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

            // Check if piece is capturable for BLACK attacker
            if (this.getColor() == Color.BLACK && this.onWall(Color.WHITE) &&
                board[row][col].inCastle(Color.WHITE)){
                // BLACK piece attacking WHITE castle
                validMoves.add(move);
            }

            // Check if piece is capturable for WHITE attacker
            else if (this.getColor() == Color.WHITE && this.onWall(Color.BLACK) &&
                     board[row][col].inCastle(Color.BLACK)) {
                // WHITE piece attacking BLACK castle
                validMoves.add(move);
            }

            // Check if piece is capturable for BLACK defender
            if (this.getColor() == Color.BLACK && this.inCastle(Color.BLACK) &&
                board[row][col].onWall(Color.BLACK)){
                // BLACK piece defending BLACK castle
                validMoves.add(move);
            }

            // Check if piece is capturable for WHITE defender
            else if (this.getColor() == Color.WHITE && this.inCastle(Color.WHITE) &&
                     board[row][col].onWall(Color.WHITE)) {
                // WHITE piece defending WHITE castle
                validMoves.add(move);
            }
        }

        // @Todo: Once board is implemented, add check to ensure move doesn't result in check

        return continueAdding;
    }
}