package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Game {
    private LocalDateTime startTime;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private int turn; // 0 if white, 1 if black
    private int GameID;

    public Game(User user) {
        startTime = null;
        board = null;
        playerOne = new Player(user, this, Color.WHITE);
        playerTwo = new Player(Color.BLACK);
    }

    public Game(int GameID, String startTimeString, String board, User player1, User player2, int turn) {
        // Do we need to use the playerIDs?
        this.GameID = GameID;
        //this.startTime = LocalDateTime.parse(startTimeString); // TODO Fix this conversion
        this.board = new Board(board);
        if(turn == 0) {
            this. playerOne = new Player(player1, this, Color.WHITE);
            this. playerTwo = new Player(player2, this, Color.BLACK);
            this.turn = 0;
        } else {
            this. playerOne = new Player(player1, this, Color.WHITE);
            this. playerTwo = new Player(player2, this, Color.BLACK);
            this.turn = 1;
        }
    }

    /*******************
     * Accessors
     ******************/
    public LocalDateTime getGameDuration() {
        return startTime; // TODO this needs to be updated to minus current time from start time
    }

    public String getRules() {
        return "White begins. Players move, and must move, in turn.\n" +
                "\n" +
                "The King is confined to his 3x3 castle. He may go and capture using either the King's move or the Knight's move.\n" +
                "\n" +
                "It's customary to look at the King in terms of the squares it does not  cover. In the center it covers the whole castle, on the side he does not cover the square on the opposite side, and in the corner it does not cover the other corner squares.\n" +
                "\n" +
                "The rook moves like the rook in Chess, unhindered by castles and walls. If a rook ends its move inside the opponent's castle, it is promoted to Queen.\n" +
                "\n" +
                "The Queen moves like the Queen in Chess, unhindered by castles and walls.\n" +
                "\n" +
                "The mutual right of capture exists, and only exists, between an attacking piece on the wall and a defending piece inside the castle. Apart from this situation pieces simply block one another.";
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }
    
    public void setBoard(Board board) {
        this.board = board;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public Player getPlayerOne() { return playerOne; }

    public Player getPlayerTwo() { return playerTwo; }

    /*******************
     * Public Methods
     ******************/
    public Player getPlayer(User user){
        if(playerOne.getUser().equals(user))
            return playerOne;
        else if(playerTwo.getUser().equals(user))
            return playerTwo;
        else
            return null;
    }

    public boolean startGame(User userTwo) {
        if (userTwo != playerOne.getUser() || !isStarted()) {
            this.playerTwo = new Player(userTwo, this, Color.BLACK);
            this.board = new Board();
            this.startTime = LocalDateTime.now();
            this.turn = 0;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isStarted(){
        return startTime != null;
    }

    public boolean isTurn(Player player) {
        if(player.equals(playerOne)){
            return turn == 0;
        }
        else if(player.equals(playerTwo)){
            return turn == 1;
        }
        else
            return false;
    }

    public boolean makeMove(Player player, Piece piece, int[] move) {
        if(isTurn(player) && piece.isValid(move, board.getBoard())){
            //Check if move causes check, and then notify the other player
            if(piece.causesCheck(move, player.getColor(), board.getBoard()))
                sendCheckNotification(player);
            //Make the move
            piece.move(move, board.getBoard());

            //Update game after move
            turn = Math.abs(turn - 1);
            Color opponentColor;

            if (player.getColor() == Color.BLACK) {
                opponentColor = Color.WHITE;
            }
            else {
                opponentColor = Color.BLACK;
            }
            if(isCheckMate(opponentColor))
                endGame(player, "checkmate");
            else if (isStaleMate(opponentColor))
                endGame(player, "stalemate");
            else{//Update game if the move is valid and the game is still going
                Database db = new Database();
                db.updateGameInDatabase(GameID, board.convertBoardToString(), turn);
            }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isCheckMate(Color oppoonentColor) {
        // first determine if the opposing king is in check
        if (oppoonentColor == Color.BLACK){
            if (!board.getBlackKing().inCheck(board.getBoard())){
                return false;
            }
        }
        else {
            if(!board.getWhiteKing().inCheck(board.getBoard())) {
                return false;
            }
        }

        ArrayList<int[]> pieces = board.getAllPieces(oppoonentColor);

        for (int i = 0; i < pieces.size(); i++) {
            int pieceRow = pieces.get(i)[0];
            int pieceCol = pieces.get(i)[1];
            Piece piece = board.getPiece(pieceRow, pieceCol);

            // see if any opponent pieces, king included, can move anywhere

            /*
             *  if no piece has any valid moves, then there is nothing that player
             *  can do to stop the checkmate so the game is over
             */

            if (!piece.validMoves(board.getBoard()).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean isStaleMate(Color opponentColor) {
        /*
         *  Stalemate occurs when both of the following circumstances are satisfied
         *
         *  1. King is last piece on the board.
         *
         *  2. King is not in check, but every other move it can make puts it into check
         */

        // Check if king is the last piece on the board
        if (board.getAllPieces(opponentColor).size() > 1) {
            return false;
        }

        // Check if King is not in check and can't move anywhere
        if (opponentColor == Color.BLACK){
            if (board.getBlackKing().inCheck(board.getBoard()) ||
                    !(board.getBlackKing().validMoves(board.getBoard()).isEmpty())){
                return false;
            }
        }
        else {
            if (board.getWhiteKing().inCheck(board.getBoard()) ||
                    !(board.getWhiteKing().validMoves(board.getBoard()).isEmpty())){
                return false;
            }
        }

        return true;
    }

    /*******************
     * Helper Methods
     ******************/
    private void sendCheckNotification(Player player) {
        if(player.equals(playerOne))
            playerTwo.getUser().receiveNotification(
                    new Notification(playerOne.getUser().getNickName() + " moved. Now your King is in check!"));
        else
            playerOne.getUser().receiveNotification(
                    new Notification(playerTwo.getUser().getNickName() + " moved. Now your Kind is in check!"));
    }

    private void endGame(Player player, String endType) {
        sendCheckMateNotification(player);
        System.out.println("END GAME");
    }

    private void sendCheckMateNotification(Player player) {
        if(player.equals(playerOne)) {
            playerOne.getUser().receiveNotification(
                    new Notification("Congrats, you won against " + playerTwo.getUser().getNickName() + "!"));
            playerTwo.getUser().receiveNotification(
                    new Notification(playerOne.getUser().getNickName() + " defeated you! Better luck next time!"));
        }
        else {
            playerOne.getUser().receiveNotification(
                    new Notification("Congrats, you won against " + playerOne.getUser().getNickName() + "!"));
            playerTwo.getUser().receiveNotification(
                    new Notification(playerOne.getUser().getNickName() + " defeated you! Better luck next time!"));
        }
    }

    public static void main(String[] args) {
        //Setup
        User userOne = new User("Tommy", "Tommy@gmail.com");
        User userTwo = new User("Lindsey", "Lindsey@gmail.com");
        Game game = new Game(userOne);
        game.startGame(userTwo);
        Player playerOne = game.getPlayer(userOne);
        Player playerTwo = game.getPlayer(userTwo);

        int counter = 0;
        Scanner sc = new Scanner(System.in);
        boolean turn = true;
        //Game Loop
        while(!game.isCheckMate(Color.BLACK) || !game.isCheckMate(Color.WHITE)){
            //Print board
            game.getBoard().printBoard();
            System.out.println();
            System.out.println();
            //Print turn
            if(counter % 2 == 0)
                System.out.println("WHITE's move.");
            else
                System.out.println("BLACK's move.");

            //Get piece and move it
            turn = true;
            while(turn){
                System.out.print("Enter the position of the piece you want to move(x,y): ");
                String[] location = sc.nextLine().toString().split(",");
                if(counter % 2 == 0){
                    Piece piece = playerOne.getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                    if (piece == null)
                        continue;
                    ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                    System.out.print("Valid moves for " + piece + ": ");
                    for (int i = 0; i < validMoves.size(); i++){
                        System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                    }
                    System.out.println();
                    System.out.print("Enter the position to move the piece(x,y): ");
                    String[] destination = sc.nextLine().toString().split(",");
                    if(playerOne.makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                        turn = false;
                }
                else{
                    Piece piece = playerTwo.getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                    if (piece == null)
                        continue;
                    ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                    System.out.print("Valid moves for " + piece + ": ");
                    for (int i = 0; i < validMoves.size(); i++){
                        System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                    }
                    System.out.println();
                    System.out.print("Enter the position to move the piece(x,y): ");
                    String[] destination = sc.nextLine().toString().split(",");
                    if(playerTwo.makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                        turn = false;
                }
            }
            counter++;
        }
    }    
}