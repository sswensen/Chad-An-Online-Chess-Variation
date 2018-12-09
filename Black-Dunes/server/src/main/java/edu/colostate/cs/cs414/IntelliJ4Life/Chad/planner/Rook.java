package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rook extends Piece {

    /*******************
     * Constructors
     ******************/
    /**
     * Constructor to create the Rook object
     *
     * @param color - color for the piece to be
     */
    public Rook(Color color) {
        initializePiece(color);
    }

    /**
     * Constructor to create the Rook object
     *
     * @param color - color for the piece to be
     * @param position - position to start the piece at
     */
    public Rook(Color color, int[] position) {
        initializePiece(color, position);
    }

    /*******************
     * Overrides
     ******************/
    /**
     * Returns the valid moves for the piece
     *
     * @param board - 2D Piece array representing the board
     * @return - ArrayList of valid moves for the piece
     */
    @Override
    public ArrayList<int[]> validMoves(Piece[][] board) {
        int[] position = this.getPosition();
        int row = position[0];
        int col = position[1];
        ArrayList<int[]> returnList = new ArrayList<int[]>();

        /*
         * Loop over potential rows
         */

        // loop over rows less than the current row
        for (int i = row - 1; i >= 0; i--) {
            int[] move = {i, col};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }
        }

        // Loop over rows greater than the current row
        for (int i = row + 1; i < 12; i++) {
            int[] move = {i, col};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }
        }

        /*
         * Loop over potential columns
         */

        // loop over columns less than the current column
        for (int i = col - 1; i >= 0; i--) {
            int[] move = {row, i};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }
        }

        // loop over columns greater than the current column
        for (int i = col + 1; i < 12; i++) {
            int[] move = {row, i};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }
        }

        return returnList;
    }

    /**
     * toString method for the piece
     *
     * @return - String representation of the piece
     */
    @Override
    public String toString() {
        if (this.getColor() == Color.BLACK) {
            return "BR";
        }
        else {
            return "WR";
        }
    }
}
