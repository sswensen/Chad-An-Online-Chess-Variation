package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import spark.Request;

public class MakeMoveSession {
    private MoveData moveData;
    private Result result;

    /**
     * Handles a request to make a move, using the userID, gameID, and initial piece location
     * and the location desired to move to. Response is data structure containing the updated
     * board and if the move completed successfully
     *
     * @param request
     */

    public MakeMoveSession(Request request, ActiveGames activeGames) {
        // first print the request
//        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());
        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        moveData = gson.fromJson(requestBody, MoveData.class);

        Database db = new Database();
        int userIdInt = Integer.parseInt(moveData.userID);
//        Game game = activeGames.getGameFromGameID(moveData.gameID);
        Game game = db.getGameFromDatabaseByID(Integer.parseInt(moveData.gameID));


        if(game == null) {
            result = new Result(game.getBoard().convertBoardToString(), false, game.getTurn());
            return;
        }

        User user = db.getUserFromDatabaseByID(userIdInt);

        int initialRow = Integer.parseInt(moveData.initialRow);
        int initialCol = Integer.parseInt(moveData.initialCol);
        int afterRow = Integer.parseInt(moveData.afterRow);
        int afterCol = Integer.parseInt(moveData.afterCol);

        Piece piece = game.getBoard().getBoard()[initialRow][initialCol];
        int[] move = {afterRow, afterCol};

        Player p = game.getPlayer(user);

        if(piece.getColor() != p.getColor()) {
            result = new Result(game.getBoard().convertBoardToString(), false, game.getTurn());
            return;
        }

        boolean makeMoveResult = p.makeMove(piece, move);

        Game databaseGame = db.getGameFromDatabaseByID(Integer.parseInt(moveData.gameID));
//        if(!databaseGame.getBoard().convertBoardToString().equals(game.getBoard().convertBoardToString())) {
//            System.err.println("GAME IN DATABASE DOESN'T MATCH!");
//        }
//        game.getBoard().printBoard();
//        databaseGame.getBoard().printBoard();
        result = new Result(game.getBoard().convertBoardToString(), makeMoveResult, game.getTurn());
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
        private String gameID = "";
        private String userID = "";
        private String initialRow = "";
        private String initialCol = "";
        private String afterRow = "";
        private String afterCol = "";
    }

    private class Result {
        private String board;
        private boolean IsSuccess;
        private int turn;

        private Result(String _board, boolean _IsSuccess, int _turn) {
            board = _board;
            IsSuccess = _IsSuccess;
            turn = _turn;
        }
    }
}