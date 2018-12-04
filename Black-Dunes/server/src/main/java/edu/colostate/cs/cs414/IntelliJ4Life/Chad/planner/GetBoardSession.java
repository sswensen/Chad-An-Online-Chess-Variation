package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import org.json.JSONObject;
import spark.Request;

public class GetBoardSession {
    private GameInfo gameInfo;
    private BoardResponse boardResponse;

    /**
     * Handles returning the board and the turn to the frontend
     *
     * @param request
     */

    public GetBoardSession(Request request, ActiveGames activeGames) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        gameInfo = gson.fromJson(requestBody, GameInfo.class);
        if(gameInfo.gameID != null) {
            Game game = activeGames.getGameFromGameID(gameInfo.gameID);

            String board = game.getBoard().convertBoardToString();
            int turn = game.getTurn();

            boardResponse = new BoardResponse(board, turn);
        } else {
            boardResponse = new BoardResponse(null, -1);
            System.err.println("Did not get board ID from frontend!");
        }
    }

    /**
     * Handles the response for a Board object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getBoard() {
        Gson gson = new Gson();
        return gson.toJson(boardResponse);
    }

    private class GameInfo {
        private String gameID = "";
    }

    private class BoardResponse {
        private String board;
        private int turn;

        private BoardResponse(String _board, int _turn) {
            board = _board;
            turn  = _turn;
        }
    }
}
