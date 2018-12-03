package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;

public class GetValidMovesSession {
    private PieceData pieceData;
    private int[][] validMoves;

    /**
     * Calculates the valid moves from the given GameID, UserID, and piece location
     *
     * @param request
     */

    public GetValidMovesSession(Request request, ActiveGames activeGames) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        pieceData = gson.fromJson(requestBody, PieceData.class);

        int userIdInt = Integer.parseInt(pieceData.userID);
        Database db = new Database();

        Game game = activeGames.getGameFromGameID(pieceData.gameID);

        if(game == null) {
            validMoves = new int[0][0];
        } else {
            User user = db.getUserFromDatabaseByID(userIdInt);
            Piece piece = game.getBoard().getBoard()[Integer.parseInt(pieceData.row)][Integer.parseInt(pieceData.col)];
            Player p = game.getPlayer(user);
            System.out.println("p:" + p);
            System.out.println("piece: " + piece);
            if(piece.getColor() != p.getColor()) {
                validMoves = new int[0][0];
            } else {
                // Find valid moves
                ArrayList<int[]> validMovesList = piece.validMoves(game.getBoard().getBoard());
                int[][] validMovesArray = new int[validMovesList.size()][2];
                validMovesArray = validMovesList.toArray(validMovesArray);

                validMoves = validMovesArray;
            }
        }
    }

    /**
     * Handles the response for valid moves.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getValidMoves() {
        Gson gson = new Gson();
        return gson.toJson(validMoves);
    }

    private class PieceData {
        private String gameID = "";
        private String userID = "";
        private String row = "-1";
        private String col = "-1";
    }
}
