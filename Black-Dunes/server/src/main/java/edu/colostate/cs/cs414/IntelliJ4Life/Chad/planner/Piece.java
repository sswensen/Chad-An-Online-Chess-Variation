package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public abstract class Piece {
    enum Color {
        WHITE, BLACK;
    }
    
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
     * Public Functions
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
    private boolean onWall(){
        int[][] walls = {{1, 7}, {1, 8}, {1, 9},
                         {2, 6}, {3, 6}, {4, 6},
                         {2, 10}, {3, 10}, {4, 10},
                         {5, 7}, {5, 8}, {5, 9},
                         {6, 2}, {6, 3}, {6, 4},
                         {7, 1}, {8, 1}, {9, 1},
                         {7, 5}, {8, 5}, {9, 5},
                         {10, 2}, {10, 3}, {10, 4}};

        for (int i = 0; i < walls.length; i++) {
            int[] location = walls[i];

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

        // Determines whether the calling function should continue adding moves
        boolean continueAdding = false;

        // Check if the space is empty
        if (board[row][col] == null) {
            validMoves.add(move);
            continueAdding = true;
        }
        // Check if the space is occupied by an opponent piece
        else if (board[row][col].getColor() != this.getColor()) {
            // Check if piece is capturable: ie, whether one of the two pieces is on a wall
            if ((this.onWall()) || (board[row][col].onWall())) {
                validMoves.add(move);
            }
        }

        // @Todo: Once board is implemented, add check to ensure move doesn't result in check

        return continueAdding;
    }
}