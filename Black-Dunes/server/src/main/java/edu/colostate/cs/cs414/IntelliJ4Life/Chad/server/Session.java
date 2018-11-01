package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Session {
    public static void main(String[] args) {
        User u = new User(1, "sswensen", "TestNickname1", "sswensen@email.com");
        //User u2 = new User(2, "cwlarson", "TestNickname2", "cwlarson@email.com");
        Database db = new Database(u);
        System.out.println("Connecting session...");
        System.out.println("Authenticating user...");
        db.getCurrentGamesFromDatabase();
        ArrayList<Game> games = db.getGames();

        // Print games
        //System.out.println("\n\n\n");
        System.out.println(u.getNickName() + " has " + games.size() + " games to play:");
        int choice = 0;
        for (Game game: games) {
            System.out.println(choice++ + " - " + game.getPlayerOne().getUser().getNickName() + " vs. "
                + game.getPlayerTwo().getUser().getNickName());
        }

        // Select game
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt(); sc.nextLine();
        Game game = games.get(choice);
        System.out.println("Resuming - " + game.getPlayerOne().getUser().getNickName() + " vs. "
                + game.getPlayerTwo().getUser().getNickName());

        // Resume game
        boolean playing = true;
        while(playing){
            // Get current game
            db.getCurrentGamesFromDatabase();
            game = db.getGames().get(choice);
            game.getBoard().printBoard();

            // Determine turn
            if(game.getTurn() == 0){ //PlayerOne's turn
                System.out.println("WHITE's turn");
                System.out.print("Enter the position of the piece you want to move(x,y): ");
                String[] location = sc.nextLine().toString().split(",");
                //Get piece to move
                Piece piece = game.getPlayerOne().getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                System.out.print("Valid moves for " + piece + ": ");
                for (int i = 0; i < validMoves.size(); i++){
                    System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                }
                System.out.println();
                if (piece == null)
                    continue;
                System.out.print("Enter the position to move the piece(x,y): ");
                String[] destination = sc.nextLine().toString().split(",");
                //Make move
                if(!game.getPlayerOne().makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                    game.setTurn(Math.abs(game.getTurn() - 1));
            }
            else{ //PlayerTwo's turn
                System.out.println("BLACK's turn");
                System.out.print("Enter the position of the piece you want to move(x,y): ");
                String[] location = sc.nextLine().toString().split(",");
                Piece piece = game.getPlayerTwo().getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                //Get piece to move
                ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                System.out.print("Valid moves for " + piece + ": ");
                for (int i = 0; i < validMoves.size(); i++){
                    System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                }
                System.out.println();
                if (piece == null)
                    continue;
                System.out.print("Enter the position to move the piece(x,y): ");
                String[] destination = sc.nextLine().toString().split(",");
                //Make move
                if(!game.getPlayerTwo().makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                    game.setTurn(Math.abs(game.getTurn() - 1));
            }
        }
    }
}
