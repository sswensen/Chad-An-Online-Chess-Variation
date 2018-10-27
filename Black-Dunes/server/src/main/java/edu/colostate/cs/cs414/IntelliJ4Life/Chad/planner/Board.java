package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Board {

    private Piece[][] spaces;

    public Board() {
        this.spaces = new Piece[12][12];

        int [][] blackStartingPositions = {
                {2, 7}, {2, 8}, {2, 9},
                {3, 7}, {3, 8}, {3, 9},
                {4, 7}, {4, 8}, {4, 9}
        };

        int [][] whiteStartingPositions = {
                {7, 2}, {7, 3}, {7, 4},
                {8, 2}, {8, 3}, {8, 4},
                {9, 2}, {9, 3}, {9, 4}
        };

        for(int row = 0; row < blackStartingPositions.length; row++) {
            int x = blackStartingPositions[row][0];
            int y = blackStartingPositions[row][1];
            if (blackStartingPositions[row] != blackStartingPositions[4]) {
                spaces[x][y] = new Rook(Color.BLACK, blackStartingPositions[row]);
            } else {
                spaces[x][y] = new King(Color.BLACK, blackStartingPositions[row]);
            }
        }

        for(int row = 0; row < whiteStartingPositions.length; row++) {
            int x = whiteStartingPositions[row][0];
            int y = whiteStartingPositions[row][1];
            if (whiteStartingPositions[row] != whiteStartingPositions[4]) {
                spaces[x][y] = new Rook(Color.WHITE, whiteStartingPositions[row]);
            } else {
                spaces[x][y] = new King(Color.WHITE, whiteStartingPositions[row]);
            }
        }
    }

    public Object[][] getBoard() {
        return this.spaces;
    }

    public Piece getPiece(int x, int y) {
        if(spaces[x][y] instanceof Piece && spaces[x][y] != null)
            return this.spaces[x][y];
        else
            return null;
    }

    public void printBoard() {
        String[][] tempBoard = new String[12][12];
        for (int i = 0; i < tempBoard.length; i++) {
            for(int j = 0; j < tempBoard[0].length; j++) {
                if(this.spaces[i][j] == null)
                    tempBoard[i][j] = "-";
                else
                    tempBoard[i][j] = this.spaces[i][j].toString();
            }
        }

        int [][] blackCastle = {
                {1, 7}, {1, 8}, {1, 9},
                {2, 6}, {3, 6}, {4, 6},
                {2, 10}, {3, 10}, {4, 10},
                {5, 7}, {5, 8}, {5, 9},
        };

        int [][] whiteCastle = {
                {6, 2}, {6, 3}, {6, 4},
                {7, 1}, {8, 1}, {9, 1},
                {10, 2}, {10, 3}, {10, 4},
                {7, 5}, {8, 5}, {9, 5}
        };


        for(int row = 0; row < blackCastle.length; row++) {
            int x = blackCastle[row][0];
            int y = blackCastle[row][1];
            tempBoard[x][y] = "##";
         }

        for(int row = 0; row < whiteCastle.length; row++) {
            int x = whiteCastle[row][0];
            int y = whiteCastle[row][1];
            tempBoard[x][y] = "##";
        }

        for(String[] row : tempBoard) {
            for (String i : row) {
                System.out.print(i);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
    }
}
