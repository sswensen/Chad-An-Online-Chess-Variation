package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Color;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Queen;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.King;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class KingTest {
    King K1;
    King K2;
    Queen Q1;
    Queen Q2;
    Queen Q3;
    Queen Q4;

    Piece[][] board;

    @Before
    public void setUp(){
        K1 = new King(Color.BLACK);
        K2 = new King(Color.WHITE);
        Q1 = new Queen(Color.BLACK);
        Q2 = new Queen(Color.WHITE);
        Q3 = new Queen(Color.WHITE);
        Q4 = new Queen(Color.WHITE);

        board = new Piece[12][12];

        // initialize board to null
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                board[i][j] = null;
            }
        }
    }

    @Test
    public void testCenter() {
        board[3][8] = K1;
        int[] position = {3, 8};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {{2, 7}, {2, 8}, {2, 9},
                                {3, 7},         {3, 9},
                                {4, 7}, {4, 8}, {4, 9}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testCenterLeft() {
        board[3][7] = K1;
        int[] position = {3, 7};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {{2, 7}, {2, 8}, {2, 9},
                                        {3, 8},
                                {4, 7}, {4, 8}, {4, 9}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testCenterRight() {
        board[3][9] = K1;
        int[] position = {3, 9};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {{2, 7}, {2, 8}, {2, 9},
                                        {3, 8},
                                {4, 7}, {4, 8}, {4, 9}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testCenterTop() {
        board[2][8] = K1;
        int[] position = {2, 8};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {{2, 7},         {2, 9},
                                {3, 7}, {3, 8}, {3, 9},
                                {4, 7},         {4, 9}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testCenterBottom() {
        board[2][8] = K1;
        int[] position = {2, 8};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {{2, 7},         {2, 9},
                                {3, 7}, {3, 8}, {3, 9},
                                {4, 7},         {4, 9}};

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testTopLeft() {
        board[2][7] = K1;
        int[] position = {2, 7};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {        {2, 8},
                                {3, 7}, {3, 8}, {3, 9},
                                        {4, 8}        };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testTopRight() {
        board[2][9] = K1;
        int[] position = {2, 9};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {        {2, 8},
                                {3, 7}, {3, 8}, {3, 9},
                                        {4, 8}        };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testBottomLeft() {
        board[4][7] = K1;
        int[] position = {4, 7};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {        {2, 8},
                                {3, 7}, {3, 8}, {3, 9},
                                        {4, 8}        };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testBottomRight() {
        board[4][9] = K1;
        int[] position = {4, 9};
        K1.setPosition(position);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {        {2, 8},
                                {3, 7}, {3, 8}, {3, 9},
                                        {4, 8}        };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededSameTeam() {
        board[4][7] = K1;
        board[3][7] = Q1;
        int[] position1 = {4, 7};
        int[] position2 = {3, 7};
        K1.setPosition(position1);
        Q1.setPosition(position2);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {        {2, 8},
                                        {3, 8}, {3, 9},
                                        {4, 8}        };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testImpededOpponentTeam() {
        board[4][7] = K1;
        board[3][7] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {3, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {
                                {3, 7},
                                                   };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testCantMoveIntoCheck() {
        board[4][7] = K1;
        board[6][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {6, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        ArrayList<int[]> validMoves = K1.validMoves(board);
        int[][] correctMoves = {
                                {3, 7},         {3, 9}
                                                      };

        int[][] validMovesArray = new int[validMoves.size()][2];
        validMovesArray = validMoves.toArray(validMovesArray);

        assertArrayEquals(correctMoves, validMovesArray);
    }

    @Test
    public void testInCheckTrue1() {
        board[3][8] = K1;
        board[3][3] = Q2;
        int[] position1 = {3, 8};
        int[] position2 = {3, 3};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertTrue(K1.inCheck(board));
    }

    @Test
    public void testInCheckTrue2() {
        board[3][8] = K1;
        board[0][5] = Q2;
        int[] position1 = {3, 8};
        int[] position2 = {0, 5};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertTrue(K1.inCheck(board));
    }

    @Test
    public void testInCheckFalse1() {
        board[3][8] = K1;
        board[0][5] = Q1;
        int[] position1 = {3, 8};
        int[] position2 = {0, 5};
        K1.setPosition(position1);
        Q1.setPosition(position2);

        assertFalse(K1.inCheck(board));
    }

    @Test
    public void testInCheckFalse2() {
        board[3][8] = K1;
        board[0][6] = Q2;
        int[] position1 = {3, 8};
        int[] position2 = {0, 6};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertFalse(K1.inCheck(board));
    }

    @Test
    public void testCheckmate1() {
        board[4][7] = K1;
        board[2][7] = Q2;
        board[2][8] = Q3;
        board[2][9] = Q4;
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        int[] position4 = {2, 9};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        Q4.setPosition(position4);

        assertTrue(K1.checkmate(board));
    }

    @Test
    public void testCheckmate2() {
        board[4][7] = K1;
        board[5][7] = Q2;
        board[4][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {4, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        assertTrue(K1.checkmate(board));
    }

    @Test
    public void testCheckmate3() {
        board[4][7] = K1;
        board[5][7] = Q2;
        board[5][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {5, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        assertTrue(K1.checkmate(board));
    }

    @Test
    public void testNotCheckmate() {
        board[4][7] = K1;
        board[5][7] = Q2;
        board[6][7] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {6, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        assertFalse(K1.checkmate(board));
    }

    @Test
    public void testNotCheckmate2() {
        board[4][7] = K1;
        board[5][7] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertFalse(K1.checkmate(board));
    }

    @Test
    public void testNotCheckmate3() {
        board[4][7] = K1;
        board[6][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {6, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertFalse(K1.checkmate(board));
    }

    @Test
    public void testNotCheckmate4() {
        board[4][7] = K1;
        board[2][8] = Q2;
        board[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        int[] position3 = {1, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        assertFalse(K1.checkmate(board));
    }

    @Test
    public void testStalemate() {
        board[4][7] = K1;
        board[2][8] = Q2;
        board[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        int[] position3 = {1, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        assertTrue(K1.stalemate(board));
    }

    @Test
    public void testNotStalemate1() {
        board[4][7] = K1;
        board[2][2] = Q1;
        board[2][8] = Q2;
        board[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 2};
        int[] position3 = {2, 8};
        int[] position4 = {1, 8};
        K1.setPosition(position1);
        Q1.setPosition(position2);
        Q2.setPosition(position3);
        Q3.setPosition(position4);

        assertFalse(K1.stalemate(board));
    }

    @Test
    public void testNotStalemate2() {
        board[4][7] = K1;
        board[2][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {6, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);

        assertFalse(K1.stalemate(board));
    }

    @Test
    public void testToString1() {
        assertTrue(K1.toString().equals("BK"));
    }

    @Test
    public void testToString2() {
        assertTrue(K2.toString().equals("WK"));
    }
}
