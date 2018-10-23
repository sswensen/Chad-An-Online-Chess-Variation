package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(Color color) {
        initializePiece(color);
    }

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

        /*
         * Loop over diagonals
         */

        // Up and left
        int i = row - 1;
        int j = col - 1;

        while (i >= 0 && j >= 0) {
            int[] move = {i, j};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }

            i--;
            j--;
        }

        // Up and right
        i = row - 1;
        j = col + 1;

        while (i >= 0 && j < 12) {
            int[] move = {i, j};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }

            i--;
            j++;
        }

        // Down and left
        i = row + 1;
        j = col - 1;

        while (i < 12 && j >= 0) {
            int[] move = {i, j};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }

            i++;
            j--;
        }

        // Down and right
        i = row + 1;
        j = col + 1;

        while (i < 12 && j < 12) {
            int[] move = {i, j};

            if (!(addMoveToList(move, board, returnList))) {
                break;
            }

            i++;
            j++;
        }

        return returnList;
    }
}
