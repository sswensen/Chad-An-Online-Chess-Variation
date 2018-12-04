package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;

public class GetGamesSession {
    private UserData userData;
    private String[][] gamesArray;

    /**
     * Gets all the games to return to the frontend in form of [gameID, user1ID, user2ID]
     *
     * @param request
     */

    public GetGamesSession(Request request, ActiveGames activeGames) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        userData = gson.fromJson(requestBody, UserData.class);

        //get userID
        int userID = Integer.parseInt(userData.userID);

        //connect to db and update game
        Database db = new Database();

        //get user
        User user = db.getUserFromDatabaseByID(userID);

        gamesArray = new String[0][0];

        if (user != null) {
            System.out.println("user.userID: " + user.getUserID());
            System.out.println();

            // Get games for user
            ArrayList<Game> games = activeGames.getGamesFromUserID(String.valueOf(user.getUserID()));

            // Array containing [gameID, user1ID, user2ID] for each game
            gamesArray = new String[games.size()][3];
            int i = 0;

            for (Game g : games) {
                gamesArray[i][0] = Integer.toString(g.getGameID());
                gamesArray[i][1] = g.getPlayerOne().getUser().getNickName();
                gamesArray[i][2] = g.getPlayerTwo().getUser().getNickName();

                i++;
            }
        }
    }

    /**
     * Handles the response for getGames.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getGames() {
        Gson gson = new Gson();
        return gson.toJson(gamesArray);
    }

    private class UserData {
        private String userID = "";
    }
}