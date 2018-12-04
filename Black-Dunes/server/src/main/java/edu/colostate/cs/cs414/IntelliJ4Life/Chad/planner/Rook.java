package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rook extends Piece {

    /**
     * Object that represents a Rook piece
     * @param color color that determines which player the rook belongs to
     */
    public Rook(Color color) {
        initializePiece(color);
    }

    public Rook(Color color, int[] position) {
        initializePiece(color, position);
    }

    /**
     * Method that determines all the valid moves of a piece
     * @param board
     * @return
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
