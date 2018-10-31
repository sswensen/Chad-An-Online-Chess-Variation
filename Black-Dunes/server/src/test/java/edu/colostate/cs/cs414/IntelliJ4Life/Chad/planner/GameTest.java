package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Color;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Rook;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.King;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Board;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    User user;
    Game game1;
    Board board;

    King K1;
    King K2;
    Queen Q1;
    Queen Q2;
    Queen Q3;
    Queen Q4;

    Piece[][] testBoard;

    @Before
    public void setUp(){
        user = new User("bill", "bill@yolo.com");
        game1 = new Game(user);
        board = new Board();

        K1 = new King(Color.BLACK);
        K2 = new King(Color.WHITE);
        Q1 = new Queen(Color.BLACK);
        Q2 = new Queen(Color.WHITE);
        Q3 = new Queen(Color.WHITE);
        Q4 = new Queen(Color.WHITE);

        testBoard = new Piece[12][12];

        // initialize board to null
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                testBoard[i][j] = null;
            }
        }

        board.setBoard(testBoard);
        game1.setBoard(board);
    }

    @Test
    public void testCheckmate1() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][7] = Q2;
        board.getBoard()[2][8] = Q3;
        board.getBoard()[2][9] = Q4;
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        int[] position4 = {2, 9};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        Q4.setPosition(position4);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testCheckmate2() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[5][7] = Q2;
        board.getBoard()[4][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {4, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testCheckmate3() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[5][7] = Q2;
        board.getBoard()[5][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {5, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testCheckmate4() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][7] = Q2;
        board.getBoard()[2][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testCheckmate5() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][7] = Q2;
        board.getBoard()[2][8] = Q3;
        board.getBoard()[4][9] = Q1; // Queen on same team
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        int[] position4 = {4, 9};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        Q1.setPosition(position4);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[5][7] = Q2;
        board.getBoard()[6][7] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        int[] position3 = {6, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate2() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[5][7] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {5, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate3() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[6][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {6, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate4() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][8] = Q2;
        board.getBoard()[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        int[] position3 = {1, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate5() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[3][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {3, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate6() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][7] = Q2;
        board.getBoard()[2][8] = Q3;
        board.getBoard()[3][4] = Q1; // Queen on same team
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        int[] position4 = {3, 4};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        Q1.setPosition(position4);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testNotCheckmate7() {
        board.getBoard()[3][7] = K1;
        board.getBoard()[1][7] = Q2;
        board.getBoard()[1][8] = Q3;
        board.getBoard()[3][9] = Q1; // Queen on same team
        int[] position1 = {4, 7};
        int[] position2 = {2, 7};
        int[] position3 = {2, 8};
        int[] position4 = {4, 9};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        Q1.setPosition(position4);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isCheckMate(Color.BLACK));
    }

    @Test
    public void testStalemate() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][8] = Q2;
        board.getBoard()[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        int[] position3 = {1, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertTrue(game1.isStaleMate(Color.BLACK));
    }

    @Test
    public void testNotStalemate1() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][2] = Q1;
        board.getBoard()[2][8] = Q2;
        board.getBoard()[1][8] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 2};
        int[] position3 = {2, 8};
        int[] position4 = {1, 8};
        K1.setPosition(position1);
        Q1.setPosition(position2);
        Q2.setPosition(position3);
        Q3.setPosition(position4);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isStaleMate(Color.BLACK));
    }

    @Test
    public void testNotStalemate2() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][8] = Q2;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isStaleMate(Color.BLACK));
    }

    @Test
    public void testNotStalemate3() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][8] = Q2;
        board.getBoard()[2][7] = Q3;
        int[] position1 = {4, 7};
        int[] position2 = {2, 8};
        int[] position3 = {2, 7};
        K1.setPosition(position1);
        Q2.setPosition(position2);
        Q3.setPosition(position3);

        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isStaleMate(Color.BLACK));
    }

    @Test
    public void testNotStalemate4() {
        board.getBoard()[4][7] = K1;
        board.getBoard()[2][7] = Q3;
        int[] position1 = {4, 7};
        int[] position3 = {2, 7};
        K1.setPosition(position1);
        Q3.setPosition(position3);

        board.setBlackKing(K1);

        // Uncomment to view board
        // board.printBoard();

        assertFalse(game1.isStaleMate(Color.BLACK));
    }
}
