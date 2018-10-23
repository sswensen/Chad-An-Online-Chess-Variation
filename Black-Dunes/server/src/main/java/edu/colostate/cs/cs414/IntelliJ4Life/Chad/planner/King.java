package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
    public King(Color color) {
        initializePiece(color);
    }

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

        if (this.getColor() == Color.BLACK) {
            if ((row % 2) == 1) { // middle row
                if (col == 7) {
                    int[] location = {3, 9};
                    returnList.remove(location);
                }
                else if (col == 9) {
                    int[] location = {3, 7};
                    returnList.remove(location);
                }
            }

            if ((col % 2) == 0) { // middle column
                if (row == 2) {
                    int[] location = {4, 8};
                    returnList.remove(location);
                }
                else if (row == 4) {
                    int[] location = {2, 8};
                    returnList.remove(location);
                }
            }

            if (((row % 2) == 0) && ((col % 2) == 1)) {  // if the piece is on the corner of the castle
                int[] location = {2, 7};
                returnList.remove(location);
                int[] location2 = {2, 9};
                returnList.remove(location2);
                int[] location3 = {4, 7};
                returnList.remove(location3);
                int[] location4 = {4, 9};
                returnList.remove(location4);
            }
        }
        else { // White piece
            if ((row % 2) == 0) { // middle row
                if (col == 2) {
                    int[] location = {8, 4};
                    returnList.remove(location);
                }
                else if (col == 4) {
                    int[] location = {8, 2};
                    returnList.remove(location);
                }
            }

            if ((col % 2) == 1) { // middle column
                if (row == 7) {
                    int[] location = {9, 3};
                    returnList.remove(location);
                }
                else if (row == 9) {
                    int[] location = {7, 3};
                    returnList.remove(location);
                }
            }

            if (((row % 2) == 1) && ((col % 2) == 0)) {  // if the piece is on the corner of the castle
                int[] location = {7, 2};
                returnList.remove(location);
                int[] location2 = {7, 4};
                returnList.remove(location2);
                int[] location3 = {9, 2};
                returnList.remove(location3);
                int[] location4 = {9, 4};
                returnList.remove(location4);
            }
        }

        for (int[] move : returnList) {
            int moveRow = move[0];
            int moveCol = move[1];

            if (board[moveRow][moveCol] != null){
                if (board[moveRow][moveCol].getColor() == this.getColor()) {
                    int[] location = {moveRow, moveCol};
                    returnList.remove(location);
                }
            }
        }

        return returnList;
    }
}
