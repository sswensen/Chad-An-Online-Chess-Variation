package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {

    /*******************
     * Constructor
     ******************/
    public King(Color color) {
        initializePiece(color);
    }

    public King(Color color, int[] position) {
        initializePiece(color, position);
    }

    /*******************
     * Overrides
     ******************/
    @Override
    public ArrayList<int[]> validMoves(Piece[][] board) {
        int[] position = this.getPosition();
        int row = position[0];
        int col = position[1];
        int[][] castle;

        if (this.getColor() == Color.BLACK) {
            int [][] temp = {{2, 7}, {2, 8}, {2, 9},
                             {3, 7}, {3, 8}, {3, 9},
                             {4, 7}, {4, 8}, {4, 9}};
            castle = temp;
        }
        else {
            int [][] temp = {{7, 2}, {7, 3}, {7, 4},
                             {8, 2}, {8, 3}, {8, 4},
                             {9, 2}, {9, 3}, {9, 4}};
            castle = temp;
        }

        // determine valid moves by places in castle king can't move to
        ArrayList<int[]> returnList = new ArrayList<int[]>(Arrays.asList(castle));

        // First, remove the location the king is currently in
        returnList.removeIf(val -> (val[0] == position[0] && val[1] == position[1]));

        if (this.getColor() == Color.BLACK) {
            if ((row % 2) == 1) { // middle row
                if (col == 7) {
                    int[] location = {3, 9};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
                else if (col == 9) {
                    int[] location = {3, 7};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
            }

            if ((col % 2) == 0) { // middle column
                if (row == 2) {
                    int[] location = {4, 8};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
                else if (row == 4) {
                    int[] location = {2, 8};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
            }

            if (((row % 2) == 0) && ((col % 2) == 1)) {  // if the piece is on the corner of the castle
                int[] location = {2, 7};
                returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                int[] location2 = {2, 9};
                returnList.removeIf(val -> (val[0] == location2[0] && val[1] == location2[1]));
                int[] location3 = {4, 7};
                returnList.removeIf(val -> (val[0] == location3[0] && val[1] == location3[1]));
                int[] location4 = {4, 9};
                returnList.removeIf(val -> (val[0] == location4[0] && val[1] == location4[1]));
            }
        }
        else { // White piece
            if ((row % 2) == 0) { // middle row
                if (col == 2) {
                    int[] location = {8, 4};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
                else if (col == 4) {
                    int[] location = {8, 2};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
            }

            if ((col % 2) == 1) { // middle column
                if (row == 7) {
                    int[] location = {9, 3};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
                else if (row == 9) {
                    int[] location = {7, 3};
                    returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                }
            }

            if (((row % 2) == 1) && ((col % 2) == 0)) {  // if the piece is on the corner of the castle
                int[] location = {7, 2};
                returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
                int[] location2 = {7, 4};
                returnList.removeIf(val -> (val[0] == location2[0] && val[1] == location2[1]));
                int[] location3 = {9, 2};
                returnList.removeIf(val -> (val[0] == location3[0] && val[1] == location3[1]));
                int[] location4 = {9, 4};
                returnList.removeIf(val -> (val[0] == location4[0] && val[1] == location4[1]));
            }
        }

        ArrayList<int[]> movesToRemove = new ArrayList<int[]>();

        for (int i = 0; i < returnList.size(); i++) {
            int moveRow = returnList.get(i)[0];
            int moveCol = returnList.get(i)[1];

            if (board[moveRow][moveCol] != null){
                if (board[moveRow][moveCol].getColor() == this.getColor()) {
                    int[] location = {moveRow, moveCol};
                    movesToRemove.add(location);
                }
            }

            if (causesCheck(new int[] {moveRow, moveCol}, this.getColor(), board)) {
                int[] location = {moveRow, moveCol};
                movesToRemove.add(location);
            }
        }

        for (int[] move : movesToRemove) {
            int[] location = {move[0], move[1]};
            returnList.removeIf(val -> (val[0] == location[0] && val[1] == location[1]));
        }

        return returnList;
    }

    @Override
    public String toString() {
        if (this.getColor() == Color.BLACK) {
            return "BK";
        }
        else {
            return "WK";
        }
    }

    /*******************
     * Public Methods
     ******************/
    // Check if the king is in check
    public boolean inCheck(Piece[][] board) {
        int[] position = this.getPosition();
        int row = position[0];
        int col = position[1];

        /*
         * Loop over potential rows
         */

        // loop over rows less than the current row
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] != null && board[i][col].getColor() != this.getColor()) {
                return true;
            }
            else if (board[i][col] != null){
                break;
            }
        }

        // Loop over rows greater than the current row
        for (int i = row + 1; i < 12; i++) {
            if (board[i][col] != null && board[i][col].getColor() != this.getColor()) {
                return true;
            }
            else if (board[i][col] != null){
                break;
            }
        }

        /*
         * Loop over potential columns
         */

        // loop over columns less than the current column
        for (int i = col - 1; i >= 0; i--) {
            if (board[row][i] != null && board[row][i].getColor() != this.getColor()) {
                return true;
            }
            else if (board[row][i] != null){
                break;
            }
        }

        // loop over columns greater than the current column
        for (int i = col + 1; i < 12; i++) {
            if (board[row][i] != null && board[row][i].getColor() != this.getColor()) {
                return true;
            }
            else if (board[row][i] != null){
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
            if (board[i][j] != null && board[i][j].getColor() != this.getColor() &&
                board[i][j] instanceof Queen) {
                return true;
            }
            else if (board[i][j] != null) {
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

            if (board[i][j] != null && board[i][j].getColor() != this.getColor() &&
                board[i][j] instanceof Queen) {
                return true;
            }
            else if (board[i][j] != null) {
                break;
            }

            i--;
            j++;
        }

        // Down and left
        i = row + 1;
        j = col - 1;

        while (i < 12 && j >= 0) {
            if (board[i][j] != null && board[i][j].getColor() != this.getColor() &&
                board[i][j] instanceof Queen) {
                return true;
            }
            else if (board[i][j] != null) {
                break;
            }

            i++;
            j--;
        }

        // Down and right
        i = row + 1;
        j = col + 1;

        while (i < 12 && j < 12) {
            if (board[i][j] != null && board[i][j].getColor() != this.getColor() &&
                board[i][j] instanceof Queen) {
                return true;
            }
            else if (board[i][j] != null) {
                break;
            }

            i++;
            j++;
        }

        return false;
    }
}
