package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Game;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Session {
    public static void main(String[] args) {
        User u = new User(1, "sswensen", "TestNickname1", "sswensen@email.com");
        User u2 = new User(2, "cwlarson", "TestNickname2", "cwlarson@email.com");
        Database db = new Database(u);
        db.getCurrentGamesFromDatabase();
        ArrayList<Game> games = db.getGames();

        // Print games
        System.out.println("\n\n\n");
        System.out.println(u.getUsername() + " has " + games.size() + " games to play:");
        int choice = 0;
        for (Game game: games) {
            System.out.println(choice++ + " - " + game.getPlayerOne().getUser().getNickName() + " vs. "
                + game.getPlayerTwo().getUser().getNickName());
        }

        // Select game
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        Game game = games.get(choice);
        System.out.println("Resuming - " + game.getPlayerOne().getUser().getNickName() + " vs. "
                + game.getPlayerTwo().getUser().getNickName());

        // Resume game
        boolean playing = true;
        while(playing){
            game.getBoard().printBoard();

        }
    }
}
