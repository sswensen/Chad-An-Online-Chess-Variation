package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;

public class MakeMoveSession {
    private MoveData moveData;
    private Result result;

    /**
     *
     * Handles a request to make a move, using the userID, gameID, and initial piece location
     * and the location desired to move to. Response is data structure containing the updated
     * board and if the move completed successfully
     *
     * @param request
     */

    public MakeMoveSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());
        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        moveData = gson.fromJson(requestBody, MoveData.class);

        // TODO Use Scott's code to get game from database once that is completed
        int gameIdInt = Integer.parseInt(moveData.gameID);
        int userIdInt = Integer.parseInt(moveData.userID);
        Database db = new Database();
        ArrayList<Game> games = db.getGames();
        Game game = null;
        for (Game g: games) {
            if(g.getGameID() == gameIdInt)
                game = g;
        }
        if(game == null) {
            result = new Result(game.getBoard().convertBoardToString(), false);
            return;
        }

        User user = db.getUserFromDatabaseByID(userIdInt);
        Piece piece = game.getBoard().getBoard()[moveData.initialRow][moveData.initialCol];
        int[] move = {moveData.afterRow, moveData.afterCol};

        Player p = game.getPlayer(user);

        if (piece.getColor() != p.getColor()) {
            result = new Result(game.getBoard().convertBoardToString(), false);
            return;
        }

        boolean makeMoveResult = p.makeMove(piece, move);

        // TODO: Scott, get game from database again and compare to the local game object to ensure they're equal

        result = new Result(game.getBoard().convertBoardToString(), makeMoveResult);
    }

    /**
     * Handles the response for making a move.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getMakeMove() {
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    private class MoveData {
        private String gameID  = "";
        private String userID  = "";
        private int initialRow = -1;
        private int initialCol = -1;
        private int afterRow   = -1;
        private int afterCol   = -1;
    }

    private class Result {
        private String board;
        private boolean IsSuccess;

        private Result(String _board, boolean _IsSuccess) {
            board     = _board;
            IsSuccess = _IsSuccess;
        }
    }
}
