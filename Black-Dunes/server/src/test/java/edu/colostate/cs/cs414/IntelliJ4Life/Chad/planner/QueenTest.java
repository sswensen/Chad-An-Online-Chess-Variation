package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Color;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Queen;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QueenTest {
    Queen Q1;
    Queen Q2;
    Queen Q3;

    Piece[][] board;

    @Before
    public void setUp(){
        Q1 = new Queen(Color.BLACK);
        Q2 = new Queen(Color.BLACK);
        Q3 = new Queen(Color.WHITE);

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
        board[5][5] = Q1;
        int[] position = {5, 5};
        Q1.setPosition(position);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam() {
        board[5][5] = Q1;
        board[5][8] = Q2;
        int[] position1 = {5, 5};
        int[] position2 = {5, 8};
        Q1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam2() {
        board[5][5] = Q1;
        board[2][2] = Q2;
        int[] position1 = {5, 5};
        int[] position2 = {2, 2};
        Q1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam1() {
        board[5][5] = Q1;
        board[1][9] = Q2;
        int[] position1 = {5, 5};
        int[] position2 = {1, 9};
        Q1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8},
                {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam3() {
        board[5][5] = Q1;
        board[8][2] = Q2;
        int[] position1 = {5, 5};
        int[] position2 = {8, 2};
        Q1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam4() {
        board[5][5] = Q1;
        board[9][9] = Q2;
        int[] position1 = {5, 5};
        int[] position2 = {9, 9};
        Q1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {6, 6}, {7, 7}, {8, 8}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable1() {
        board[5][5] = Q1;
        board[8][2] = Q3;
        int[] position1 = {5, 5};
        int[] position2 = {8, 2};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5},
                {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5},
                {5, 4}, {5, 3}, {5, 2}, {5, 1}, {5, 0},
                {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11},
                {4, 4}, {3, 3}, {2, 2}, {1, 1}, {0, 0},
                {4, 6}, {3, 7}, {2, 8}, {1, 9}, {0, 10},
                {6, 4}, {7, 3},
                {6, 6}, {7, 7}, {8, 8}, {9, 9}, {10, 10}, {11, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable2() {
        board[1][9] = Q1;
        board[3][7] = Q3;
        int[] position1 = {1, 9};
        int[] position2 = {3, 7};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{0, 9}, {2, 9}, {3, 9}, {4, 9}, {5, 9}, {6, 9},
                {7, 9}, {8, 9}, {9, 9}, {10, 9}, {11, 9},
                {1, 8}, {1, 7}, {1, 6}, {1, 5}, {1, 4}, {1, 3}, {1, 2}, {1, 1}, {1, 0},
                {1, 10}, {1, 11},
                {0, 8}, {0, 10}, {2, 8}, {2, 10}, {3, 11}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentNotCapturable3() {
        board[8][4] = Q1;
        board[6][2] = Q3;
        int[] position1 = {8, 4};
        int[] position2 = {6, 2};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{7, 4}, {6, 4}, {5, 4}, {4, 4}, {3, 4}, {2, 4}, {1, 4}, {0, 4},
                {9, 4}, {10, 4}, {11, 4},
                {8, 3}, {8, 2}, {8, 1}, {8, 0},
                {8, 5}, {8, 6}, {8, 7}, {8, 8}, {8, 9}, {8, 10}, {8, 11},
                {7, 3}, {7, 5}, {6, 6}, {5, 7}, {4, 8}, {3, 9}, {2, 10}, {1, 11},
                {9, 3}, {10, 2}, {11, 1},
                {9, 5}, {10, 6}, {11, 7}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentCapturable1() {
        board[6][2] = Q1;
        board[8][4] = Q3;
        int[] position1 = {6, 2};
        int[] position2 = {8, 4};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{5, 2}, {4, 2}, {3, 2}, {2, 2}, {1, 2}, {0, 2},
                {7, 2}, {8, 2}, {9, 2}, {10, 2}, {11, 2},
                {6, 1}, {6, 0}, {6, 3}, {6, 4}, {6, 5},
                {6, 6}, {6, 7}, {6, 8}, {6, 9}, {6, 10}, {6, 11},
                {5, 1}, {4, 0}, {5, 3}, {4, 4}, {3, 5}, {2, 6}, {1, 7}, {0, 8},
                {7, 1}, {8, 0}, {7, 3}, {8, 4}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentCapturable2() {
        board[6][2] = Q1;
        board[8][2] = Q3;
        int[] position1 = {6, 2};
        int[] position2 = {8, 2};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{5, 2}, {4, 2}, {3, 2}, {2, 2}, {1, 2}, {0, 2}, {7, 2}, {8, 2},
                {6, 1}, {6, 0}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9}, {6, 10}, {6, 11},
                {5, 1}, {4, 0}, {5, 3}, {4, 4}, {3, 5}, {2, 6}, {1, 7}, {0, 8},
                {7, 1}, {8, 0}, {7, 3}, {8, 4}, {9, 5}, {10, 6}, {11, 7}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentCapturable3() {
        board[3][7] = Q1;
        board[1][9] = Q3;
        int[] position1 = {3, 7};
        int[] position2 = {1, 7};
        Q1.setPosition(position1);
        Q3.setPosition(position2);

        ArrayList<int[]> validMoves = Q1.validMoves(board);
        int[][] correctMoves = {{2, 7}, {1, 7}, {0, 7}, {4, 7}, {5, 7}, {6, 7},
                {7, 7}, {8, 7}, {9, 7}, {10, 7}, {11, 7},
                {3, 6}, {3, 5}, {3, 4}, {3, 3}, {3, 2}, {3, 1}, {3, 0},
                {3, 8},{3, 9}, {3, 10}, {3, 11},
                {2, 6}, {1, 5}, {0, 4}, {2, 8}, {1, 9},
                {4, 6}, {5, 5}, {6, 4}, {7, 3}, {8, 2}, {9, 1}, {10, 0},
                {4, 8}, {5, 9}, {6, 10}, {7, 11}, };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testToString1() {
        assertTrue(Q1.toString().equals("BQ"));
    }

    @Test
    public void testToString2() {
        assertTrue(Q3.toString().equals("WQ"));
    }
}
