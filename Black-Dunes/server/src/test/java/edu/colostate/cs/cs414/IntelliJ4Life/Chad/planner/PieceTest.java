package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Color;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Queen;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Rook;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.King;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PieceTest {
    Piece P1;
    Piece P2;
    Piece P3;
    Piece P4;

    Piece[][] board;

    @Before
    public void setUp(){
        P1 = new Queen(Color.BLACK);
        P2 = new Rook(Color.WHITE);
        P3 = new Rook(Color.BLACK);
        P4 = new King(Color.BLACK);

        board = new Piece[12][12];

        // initialize board to null
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                board[i][j] = null;
            }
        }
    }

    @Test
    public void testIsValidTrue() {
        board[5][5] = P1;
        int[] position1 = {5, 5};
        P1.setPosition(position1);

        int[] newLocation = {2, 5};

        assertTrue(P1.isValid(newLocation, board));
    }

    @Test
    public void testIsValidFalse() {
        board[5][5] = P1;
        int[] position1 = {5, 5};
        P1.setPosition(position1);

        int[] newLocation = {8, 9};

        assertFalse(P1.isValid(newLocation, board));
    }

    @Test
    public void testMove() {
        board[5][5] = P1;
        int[] position1 = {5, 5};
        P1.setPosition(position1);

        int[] newLocation = {2, 5};
        P1.move(newLocation, board);

        Piece[][] correctBoard;
        correctBoard = new Piece[12][12];

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                correctBoard[i][j] = null;
            }
        }

        correctBoard[2][5] = P1;

        assertArrayEquals(correctBoard, board);
    }

    @Test
    public void testCaptureReplacement() {
        board[6][2] = P1;
        board[8][2] = P2;
        int[] position1 = {6, 2};
        int[] position2 = {8, 2};
        P1.setPosition(position1);
        P2.setPosition(position2);

        int[] newLocation = {8, 2};
        P1.move(newLocation, board);

        Piece[][] correctBoard;
        correctBoard = new Piece[12][12];

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                correctBoard[i][j] = null;
            }
        }

        correctBoard[8][2] = P1;

        assertArrayEquals(correctBoard, board);
    }

    @Test
    public void testCaptureTaken() {
        board[6][2] = P1;
        board[8][2] = P2;
        int[] position1 = {6, 2};
        int[] position2 = {8, 2};
        P1.setPosition(position1);
        P2.setPosition(position2);

        int[] newLocation = {8, 2};
        P1.move(newLocation, board);

        assertTrue(P2.isTaken());
    }

    @Test
    public void testCaptureOffBoard() {
        board[6][2] = P1;
        board[8][2] = P2;
        int[] position1 = {6, 2};
        int[] position2 = {8, 2};
        P1.setPosition(position1);
        P2.setPosition(position2);

        int[] newLocation = {8, 2};
        P1.move(newLocation, board);

        assertArrayEquals(P2.getPosition(), new int[] {-1, -1});
    }

    @Test
    public void testBlackUpgrade() {
        board[5][3] = P3;
        int[] position1 = {5, 3};
        P3.setPosition(position1);

        int[] newLocation = {8, 3};
        P3.move(newLocation, board);

        assertTrue(board[8][3] instanceof Queen);
    }

    @Test
    public void testBlackNoUpgrade() {
        board[5][7] = P3;
        int[] position1 = {5, 7};
        P3.setPosition(position1);

        int[] newLocation = {3, 7};
        P3.move(newLocation, board);

        assertTrue(board[3][7] instanceof Rook);
    }

    @Test
    public void testWhiteUpgrade() {
        board[5][7] = P2;
        int[] position1 = {5, 7};
        P2.setPosition(position1);

        int[] newLocation = {3, 7};
        P2.move(newLocation, board);

        assertTrue(board[3][7] instanceof Queen);
    }

    @Test
    public void testWhiteNoUpgrade() {
        board[5][3] = P2;
        int[] position1 = {5, 3};
        P2.setPosition(position1);

        int[] newLocation = {8, 3};
        P2.move(newLocation, board);

        assertTrue(board[8][3] instanceof Rook);
    }

    @Test
    public void testCausesCheckTrue() {
        board[3][8] = P4;
        board[3][6] = P3;
        board[3][3] = P2;
        int[] position1 = {3, 8};
        int[] position2 = {3, 6};
        int[] position3 = {3, 3};
        P4.setPosition(position1);
        P3.setPosition(position2);
        P2.setPosition(position3);

        int[] move = {4, 6};

        assertTrue(P3.causesCheck(move, P3.getColor(), board));
    }
}
