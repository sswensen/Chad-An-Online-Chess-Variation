package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Piece;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Session {

    /**
     * main method to rest a game -- now obsolete
     *
     * @param args - null since no program arguments are needed
     */
    public static void main(String[] args) {
        //User u = new User(1, "sswensen", "TestNickname1", "sswensen@email.com");
        //User u2 = new User(2, "cwlarson", "TestNickname2", "cwlarson@email.com");
        System.out.println("Connecting session...");
        Database db = new Database();
        Scanner sc = new Scanner(System.in);

        System.out.println("Please chose one of the following by typing a number: \n1 : Login \n2 : Register ");
        int action = sc.nextInt();
        sc.nextLine();

        // Make new user information
        String newUsername = "newUser";
        String newNickname = "newNickname";
        String newEmail = "newEmail@email.com";
        String newPassword = "newPassword";

        if(action == 2) {
            // Pass new user information into database to create new user
            System.out.println("Registering new user...");
            if(db.registerUserInDatabase(newUsername, newNickname, newEmail, newPassword)) {
                System.out.println("Registered new user in system");
            } else {
                System.out.println("Username or password taken, please try again");
                return; // TODO update this to prompt again
            }
        }

        if(action == 1) {
            newUsername = "sswensen";
            newNickname = "";
            newEmail = "";
            newPassword = "sswensenPassword";
        }

        // Authenticate the user in the system (sets User in Database to user returned from sql query)
        System.out.println("Authenticating user...");
        User current = db.getUserFromDatabase(newUsername, newPassword);
        if(current.getUserID() < 0) {
            System.out.println("User not authenticated!");
            return; // TODO update this to prompt again
        } else {
            System.out.println("User successfully authenticated!");
            System.out.println("Authenticated as user \"" + current.getNickName() + "\" with username: \"" + current.getUsername() + "\"\n");
        }

        // TODO do we want to populate all existing games in the database into objects here or only pull the ones that the user currently is partaking in?
        // TODO this means that in the while loop, we could just do a lookup on all games instead of possible creating a game for each player (which might overlap)_
        // TODO this would also ensure only one game is booted for any two users in the same game
        // Populate db's games variable with the authenticated user's games
        db.getCurrentGamesFromDatabase();
        ArrayList<Game> games = db.getGames();

        // Print games
        //System.out.println("\n\n\n");
        System.out.println(current.getNickName() + " has " + games.size() + " games to play:");
        int choice = 0;
        Game game = null;

        if(games.size() != 0) {
            for(Game g : games) {
                System.out.println(choice++ + " - " + g.getPlayerOne().getUser().getNickName() + " vs. "
                        + g.getPlayerTwo().getUser().getNickName());
            }

            // Select game
            System.out.println("\nSelect a game by typing its ID: ");
            choice = sc.nextInt();
            sc.nextLine();
            game = games.get(choice);
            System.out.println("Resuming - " + game.getPlayerOne().getUser().getNickName() + " vs. "
                    + game.getPlayerTwo().getUser().getNickName());
        } else {
            // TODO Start new game
            System.out.println("User has no games to play. :(");
            return; // TODO update this so start new game
        }

        // Resume game
        boolean playing = true;
        while(playing) {
            // Get current game
            db.getCurrentGamesFromDatabase();
            // TODO PRIMARY What happens if database updates between when this is called and it is called again? We need to do a lookup by game id, maybe instead of storing choice, we store game id instead. GameID lookup method needs to be implemented into dtabase class
            game = db.getGames().get(choice);
            game.getBoard().printBoard();

            // Determine turn
            if(game.getTurn() == 0) { //PlayerOne's turn
                System.out.println("\nWHITE's turn");
                System.out.print("Enter the position of the piece you want to move(row,col): \n");
                String[] location = sc.nextLine().split(",");
                //Get piece to move
                Piece piece = game.getPlayerOne().getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                // TODO handle piece being null (like when selecting the opponent's color)
                if(piece == null) {
                    System.out.println("Invalid piece selection");
                    continue;
                }
                ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                System.out.print("Valid moves for " + piece + ": ");
                for(int i = 0; i < validMoves.size(); i++) {
                    System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                }
                System.out.println();
                System.out.print("Enter the position to move the piece(row,col): \n");
                String[] destination = sc.nextLine().split(",");
                //Make move
                if(!game.getPlayerOne().makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                    game.setTurn(Math.abs(game.getTurn() - 1));
            } else { //PlayerTwo's turn
                System.out.println("\nBLACK's turn");
                System.out.print("Enter the position of the piece you want to move(row,col): \n");
                String[] location = sc.nextLine().split(",");
                Piece piece = game.getPlayerTwo().getPiece(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                if(piece == null) {
                    System.out.println("Invalid piece selection");
                    continue;
                }
                //Get piece to move
                ArrayList<int[]> validMoves = piece.validMoves(game.getBoard().getBoard());
                System.out.print("Valid moves for " + piece + ": ");
                for(int i = 0; i < validMoves.size(); i++) {
                    System.out.print(Arrays.toString(validMoves.get(i)) + " ");
                }
                System.out.println();
                System.out.print("Enter the position to move the piece(row,col): \n");
                String[] destination = sc.nextLine().split(",");
                //Make move
                if(!game.getPlayerTwo().makeMove(piece, new int[]{Integer.parseInt(destination[0]), Integer.parseInt(destination[1])}))
                    game.setTurn(Math.abs(game.getTurn() - 1));
            }
        }
    }
}
