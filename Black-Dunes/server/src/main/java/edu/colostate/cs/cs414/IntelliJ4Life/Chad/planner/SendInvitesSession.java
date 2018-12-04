package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import org.json.JSONObject;
import spark.Request;

public class SendInvitesSession {
    private UserInfo userInfo;
    private boolean response;

    /**
     * Handles returning the board and the turn to the frontend
     *
     * @param request
     */

    public SendInvitesSession(Request request, ActiveGames activeGames) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        userInfo = gson.fromJson(requestBody, UserInfo.class);

        Database db = new Database();
        Game game = activeGames.getGameFromGameID(userInfo.userID);

        String board = game.getBoard().convertBoardToString();
        int turn = game.getTurn();

        boardResponse = new BoardResponse(board, turn);
    }

    /**
     * Handles the response for sending invites.
     * Does the conversion from a Java class to a Json string.*
     */
    public String sendInvitesStatus() {
        Gson gson = new Gson();
        return gson.toJson(boardResponse);
    }

    private class UserInfo {
        private String userID = "";
    }
}
