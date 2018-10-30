package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Game {
    private LocalDateTime startTime;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private int turn; // 0 if white, 1 if black

    public Game(User user) {
        startTime = null;
        board = null;
        playerOne = new Player(user, this, Color.WHITE);
        playerTwo = new Player(Color.BLACK);
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
            if(isCheckMate())
                endGame(player);
            return true;
        }
        else {
            return false;
        }
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

    private boolean isCheckMate() {
        return (board.getBlackKing().checkmate(board.getBoard()) ||
                board.getWhiteKing().checkmate(board.getBoard()));
    }

    private void endGame(Player player) {
        sendCheckMateNotification(player);
        // TODO: save game record and terminate

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
        while(true){
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
                    System.out.print("Enter the position to move the piece(x,y): ");
                    String[] destination = sc.nextLine().toString().split(",");
                    if(playerOne.makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                        turn = false;
                }
                else{
                    Piece piece = playerTwo.getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                    if (piece == null)
                        continue;
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