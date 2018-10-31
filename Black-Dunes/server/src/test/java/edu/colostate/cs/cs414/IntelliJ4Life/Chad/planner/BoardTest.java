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

public class BoardTest {
    Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testGetAllPieces1() {
        ArrayList<int[]> pieces = board.getAllPieces(Color.WHITE);

        int[][] locations = {{7, 2}, {7, 3}, {7, 4},
                             {8, 2}, {8, 3}, {8, 4},
                             {9, 2}, {9, 3}, {9, 4} };

        int[][] piecesArray = new int[pieces.size()][2];
        piecesArray = pieces.toArray(piecesArray);

        assertArrayEquals(locations, piecesArray);
    }

    @Test
    public void testGetAllPieces2() {
        ArrayList<int[]> pieces = board.getAllPieces(Color.BLACK);

        int[][] locations = {{2, 7}, {2, 8}, {2, 9},
                             {3, 7}, {3, 8}, {3, 9},
                             {4, 7}, {4, 8}, {4, 9} };

        int[][] piecesArray = new int[pieces.size()][2];
        piecesArray = pieces.toArray(piecesArray);

        assertArrayEquals(locations, piecesArray);
    }

    @Test
    public void testGetAllPieces3() {
        board.getBoard()[2][8] = null;
        board.getBoard()[4][9] = null;

        ArrayList<int[]> pieces = board.getAllPieces(Color.BLACK);

        int[][] locations = {{2, 7},         {2, 9},
                             {3, 7}, {3, 8}, {3, 9},
                             {4, 7}, {4, 8},        };

        int[][] piecesArray = new int[pieces.size()][2];
        piecesArray = pieces.toArray(piecesArray);

        assertArrayEquals(locations, piecesArray);
    }

    @Test
    public void testGetAllPieces4() {
        board.getBoard()[2][8] = null;
        board.getBoard()[4][9] = null;

        Piece P1 = board.getBoard()[3][7];
        P1.move(new int[] {3, 5}, board.getBoard());

        ArrayList<int[]> pieces = board.getAllPieces(Color.BLACK);

        int[][] locations = {{2, 7},         {2, 9},
                             {3, 5}, {3, 8}, {3, 9},
                             {4, 7}, {4, 8},        };

        int[][] piecesArray = new int[pieces.size()][2];
        piecesArray = pieces.toArray(piecesArray);

        assertArrayEquals(locations, piecesArray);
    }
}
