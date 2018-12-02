package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import org.json.JSONObject;
import spark.Request;

public class GetBoardSession {
    private RetrievedBoard retrievedBoard;
    private Board board;

    /**
     * Handles trip planning request, creating a new trip object from the trip request.
     * Does the conversion from Json to a Java class before planning the trip.
     *
     * @param request
     */

    public GetBoardSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        retrievedBoard = gson.fromJson(requestBody, RetrievedBoard.class);

        board = new Board(retrievedBoard.board);

        // @TODO: Update board in database
    }

    /**
     * Handles the response for a Board object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getBoard() {
        Gson gson = new Gson();
        return gson.toJson(retrievedBoard);
    }

    private class RetrievedBoard {
        private String userID = "";
        private String board = "";
    }
}
