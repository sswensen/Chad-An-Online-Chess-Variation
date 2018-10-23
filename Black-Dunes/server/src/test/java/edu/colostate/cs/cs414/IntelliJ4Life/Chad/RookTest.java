package edu.colostate.cs.cs414.IntelliJ4Life.Chad;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Color;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Rook;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RookTest {
    Rook R1;
    Rook R2;
    Rook R3;
    Rook R4;

    Piece[][] board;

    @Before
    public void setUp(){
        R1 = new Rook(Color.BLACK);
        R2 = new Rook(Color.BLACK);
        R3 = new Rook(Color.WHITE);
        R4 = new Rook(Color.WHITE);

        board = new Piece[12][12];

        // initialize board to null
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                board[i][j] = null;
            }
        }
    }

    @Test
    public void testNotImpeded() {
        board[5][5] = R1;
        int[] position = {5, 5};
        R1.setPosition(position);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam() {
        board[5][5] = R1;
        board[5][8] = R2;
        int[] position1 = {5, 5};
        int[] position2 = {5, 8};
        R1.setPosition(position1);
        R2.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                                {5, 6}, {5, 7}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable1() {
        board[5][5] = R1;
        board[5][8] = R3;
        int[] position1 = {5, 5};
        int[] position2 = {5, 8};
        R1.setPosition(position1);
        R2.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable2() {
        board[5][5] = R1;
        board[8][5] = R3;
        int[] position1 = {5, 5};
        int[] position2 = {8, 5};
        R1.setPosition(position1);
        R3.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable3() {
        board[1][7] = R1;
        board[3][7] = R3;
        int[] position1 = {1, 7};
        int[] position2 = {3, 7};
        R1.setPosition(position1);
        R3.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{0, 7}, {2, 7},
                {1, 6}, {1, 5}, {1, 4}, {1, 3}, {1, 2}, {1, 1}, {1, 0},
                {1, 8}, {1, 9}, {1, 10}, {1, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable4() {
        board[8][2] = R1;
        board[6][2] = R3;
        int[] position1 = {8, 2};
        int[] position2 = {6, 2};
        R1.setPosition(position1);
        R3.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{7, 2}, {9, 2}, {10, 2}, {11, 2},
                {8, 1}, {8, 0}, {8, 3}, {8, 4}, {8, 5}, {8, 6}, {8, 7}, {8, 8}, {8, 9}, {8, 10}, {8, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentCapturable1() {
        board[6][2] = R1;
        board[8][2] = R3;
        int[] position1 = {6, 2};
        int[] position2 = {8, 2};
        R1.setPosition(position1);
        R3.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{5, 2}, {4, 2}, {3, 2}, {2, 2}, {1, 2}, {0, 2}, {7, 2}, {8, 2},
                {6, 1}, {6, 0}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9}, {6, 10}, {6, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        for (int i = 0; i < validMovesArray.length; i++) {
            System.out.println(Arrays.toString(validMovesArray[i]));
        }

        assertArrayEquals(correctMoves, validMovesArray);
    }


    @Test
    public void testImpededOpponentCapturable2() {
        board[3][7] = R1;
        board[1][7] = R3;
        int[] position1 = {3, 7};
        int[] position2 = {1, 7};
        R1.setPosition(position1);
        R3.setPosition(position2);

        ArrayList<int[]> validMoves = R1.validMoves(board);
        int[][] correctMoves = {{2, 7}, {1, 7}, {4, 7}, {5, 7}, {6, 7}, {7, 7}, {8, 7}, {9, 7}, {10, 7}, {11, 7},
                {3, 6}, {3, 5}, {3, 4}, {3, 3}, {3, 2}, {3, 1}, {3, 0},
                {3, 8}, {3, 9}, {3, 10}, {3, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }
}
