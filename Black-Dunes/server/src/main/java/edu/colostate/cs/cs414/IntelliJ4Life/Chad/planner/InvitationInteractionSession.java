package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import org.json.JSONObject;
import spark.Request;

import java.time.LocalDateTime;

public class InvitationInteractionSession {
    private InteractionInfo interactionInfo;
    private InteractionResult interactionResult;

    /**
     * Handles returning the board and the turn to the frontend
     *
     * @param request
     */

    public InvitationInteractionSession(Request request) {
        // first print the request
        //System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        interactionInfo = gson.fromJson(requestBody, InteractionInfo.class);

        interactWithInvite(interactionInfo.type);
    }

    /**
     * Handles the accepting an invite.
     * Does the conversion from a Java class to a Json string.*
     */
    public void interactWithInvite(String interactionType) {
        Database db = new Database();

        int inviteID = Integer.parseInt(interactionInfo.inviteID);
        Database.NotificationRow row = db.getNotificationFromDatabaseByInviteID(inviteID);
        User user1 = db.getUserFromDatabaseByID(row.user1ID);
        User user2 = db.getUserFromDatabaseByID(row.user2ID);

        boolean result = false;

        if (interactionType.equals("accept")) {
            Game game = new Game(user1);

            game.startGame(user2);

            result = db.addGameToDatabase(user1.getUserID(), user2.getUserID(),
                    game.getStartTime(), game.getTurn(),
                    game.getBoard().convertBoardToString());
        } else if (interactionType.equals("reject")) {
            db.addNotificationToDatabase(user1.getUserID(), "Sorry chief, " + user2.getNickName() +
                    " rejected your invitation, lol.");
            result = db.deleteNotificationRowFromDatabaseByInvitationID(inviteID);
        }

        interactionResult = new InteractionResult(result);
    }

    /**
     * Handles the response for a Board object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getInfo() {
        Gson gson = new Gson();
        return gson.toJson(interactionResult);
    }

    private class InteractionInfo {
        private String type = "";
        private String inviteID = "";
    }

    private class InteractionResult {
        private boolean result;

        private InteractionResult(boolean _result) {
            result = _result;
        }
    }
}
