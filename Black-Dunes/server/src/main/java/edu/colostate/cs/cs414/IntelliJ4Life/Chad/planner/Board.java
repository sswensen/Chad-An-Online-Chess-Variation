package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.util.ArrayList;

public class Board {

    private Piece[][] spaces;
    private King whiteKing;
    private King blackKing;

    /**
     * Constructor that initializes an empty board
     */
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

        //Add all pieces to spaces using (X, Y) cords stored in blackStartingPositions and whiteStartingPositions
        for(int row = 0; row < blackStartingPositions.length; row++) {
            int x = blackStartingPositions[row][0];
            int y = blackStartingPositions[row][1];
            if (blackStartingPositions[row] != blackStartingPositions[4]) {
                spaces[x][y] = new Rook(Color.BLACK, blackStartingPositions[row]);
            } else {
                blackKing = new King(Color.BLACK, blackStartingPositions[row]);
                spaces[x][y] = blackKing;
            }
        }

        for(int row = 0; row < whiteStartingPositions.length; row++) {
            int x = whiteStartingPositions[row][0];
            int y = whiteStartingPositions[row][1];
            if (whiteStartingPositions[row] != whiteStartingPositions[4]) {
                spaces[x][y] = new Rook(Color.WHITE, whiteStartingPositions[row]);
            } else {
                whiteKing = new King(Color.WHITE, whiteStartingPositions[row]);
                spaces[x][y] = whiteKing;
            }
        }
    }

    /**
     * Constructor using a String representing the board to
     * initialize the board
     *
     * @param board - String representing the board
     */
    public Board(String board) {
        this.spaces = buildBoardFromString(board);
    }

    /*******************
     * Public Methods
     ******************/

    /**
     * Returns the black king on the board
     *
     * @return - King object for the black king
     */
    public King getBlackKing() {
        return blackKing;
    }

    /**
     * Sets the black king instance variable for the black king's
     * location on the board
     *
     * @param blackKing - King object for the black king
     */
    public void setBlackKing(King blackKing) {
        this.blackKing = blackKing;
    }

    /**
     * Returns the white king on the board
     *
     * @return - King object for the white king
     */
    public King getWhiteKing() {
        return whiteKing;
    }

    /**
     * Sets the white king instance variable for the white king's
     * location on the board
     *
     * @param whiteKing - King object for the white king
     */
    public void setWhiteKing(King whiteKing) {
        this.whiteKing = whiteKing;
    }

    /**
     * Converts the board to a string to be stored in the database
     */
    public String convertBoardToString() {
        ArrayList<int[]> locations = new ArrayList<>();
        // int[] = {xcord, ycord, piece_type[1->Rook, 2->Queen, 3->King], piece_color[0->Black, 1->White]

        for(int i = 0; i < this.spaces.length; i++) {
            for(int j = 0; j < this.spaces[0].length;j++) {
                if(this.spaces[i][j] instanceof Piece){
                    if(this.spaces[i][j] instanceof Rook) {
                        if (this.spaces[i][j].getColor() == Color.BLACK) {
                            locations.add(new int[]{i, j, 1, 0});
                        } else {
                            locations.add(new int[]{i, j, 1, 1});
                        }
                    }
                    if(this.spaces[i][j] instanceof Queen) {
                        if (this.spaces[i][j].getColor() == Color.BLACK) {
                            locations.add(new int[]{i, j, 2, 0});
                        } else {
                            locations.add(new int[]{i, j, 2, 1});
                        }
                    }
                    if(this.spaces[i][j] instanceof King) {
                        if (this.spaces[i][j].getColor() == Color.BLACK) {
                            locations.add(new int[]{i, j, 3, 0});
                        } else {
                            locations.add(new int[]{i, j, 3, 1});
                        }
                    }
                }
            }
        }

        StringBuilder listString = new StringBuilder();
        for (int[] loc : locations) {
            int xcord = loc[0];
            int ycord = loc[1];
            int pieceType = loc[2];
            int pieceColor = loc[3];
            listString.append(xcord + "," + ycord + "," + pieceType + "," + pieceColor + " ");
        }

        String boardAsString = listString.toString();
        return boardAsString.trim();
    }

    /**
     * Uses a string passed in as a parameter to build the board
     *
     * @param newBoard - String representation of the board
     * @return - 2D piece array of the board
     */
    public Piece[][] buildBoardFromString(String newBoard) {
        String[] splitArray = newBoard.split("\\s+");
        ArrayList<int[]> allLocations = new ArrayList<>();
        for(String x : splitArray) {
            String[] strArray = x.split(",");
            int[] intArray = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            allLocations.add(intArray);
        }

        Piece[][] board = new Piece[12][12];

        for(int location = 0; location < allLocations.size(); location++) {
            int xCord = allLocations.get(location)[0];
            int yCord = allLocations.get(location)[1];
            int pieceType = allLocations.get(location)[2];
            int pieceColor = allLocations.get(location)[3];
            if (pieceType == 1) {
                if (pieceColor == 0) {
                    board[xCord][yCord] = new Rook(Color.BLACK, new int[]{xCord, yCord});
                } else {
                    board[xCord][yCord] = new Rook(Color.WHITE, new int[]{xCord, yCord});
                }
            }
            if (pieceType == 2) {
                if (pieceColor == 0) {
                    board[xCord][yCord] = new Queen(Color.BLACK, new int[]{xCord, yCord});
                } else {
                    board[xCord][yCord] = new Queen(Color.WHITE, new int[]{xCord, yCord});
                }
            }
            if (pieceType == 3) {
                if (pieceColor == 0) {
                    King k = new King(Color.BLACK, new int[]{xCord, yCord});
                    board[xCord][yCord] = k;
                    blackKing = k;
                } else {
                    King k = new King(Color.WHITE, new int[]{xCord, yCord});
                    board[xCord][yCord] = k;
                    whiteKing = k;
                }
            }
        }

        return board;
    }

    /**
     * Prints the board in a human readable format
     */
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
            if(tempBoard[x][y] == "-")
                tempBoard[x][y] = "##";
        }

        for(int row = 0; row < whiteCastle.length; row++) {
            int x = whiteCastle[row][0];
            int y = whiteCastle[row][1];
            if(tempBoard[x][y] == "-")
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

    /*******************
     * Accessors
     ******************/

    /**
     * Gets the 2D Piece array representing the board
     *
     * @return - 2D Piece representation of the board
     */
    public Piece[][] getBoard() {
        return this.spaces;
    }

    /**
     * Sets the board instance variable
     *
     * @param newBoard - board to be set as the new board
     */
    public void setBoard(Piece[][] newBoard) { this.spaces = newBoard; }

    /**
     * Gets all the pieces for the board for a specific color
     * and returns them as an arraylist
     *
     * @param color - color to search for
     * @return - ArrayList of pieces for the specified color
     */
    public ArrayList<int[]> getAllPieces(Color color) {
        ArrayList<int[]> returnList = new ArrayList<>();
        for (int i = 0; i < this.spaces.length; i++) {
            for(int j = 0; j < this.spaces[0].length; j++) {
                if (this.spaces[i][j] instanceof Piece) {
                    if (this.spaces[i][j].getColor() == color) {
                        returnList.add(this.spaces[i][j].getPosition());
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * Gets the piece at the given position
     *
     * @param x - x position of the desired piece
     * @param y - y position of the desired piece
     * @return - Piece the caller is looking for
     */
    public Piece getPiece(int x, int y) {
        if(spaces[x][y] instanceof Piece && spaces[x][y] != null)
            return this.spaces[x][y];
        else
            return null;
    }

    /**
     * Main method for testing purposes
     *
     * @param args - program arguments, null for this main method
     */
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("Original board created from default constructor");
        board.printBoard();

        System.out.println("Board after converting to a single string");
        String boardAsString = board.convertBoardToString();
        System.out.println("\t" + boardAsString);


        System.out.println("New board created from string");
        board.setBoard(board.buildBoardFromString(boardAsString));
        board.printBoard();
        System.out.println("BLACK pieces: " + board.getAllPieces(Color.BLACK).size());
        System.out.println("WHITE pieces: " + board.getAllPieces(Color.WHITE).size());
    }
}
