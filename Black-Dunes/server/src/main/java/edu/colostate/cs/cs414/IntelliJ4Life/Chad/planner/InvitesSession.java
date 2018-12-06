package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;

public class InvitesSession {
    private InviteInfo inviteInfo;
    private boolean response;

    /**
     * Handles returning the board and the turn to the frontend
     *
     * @param request
     */

    public InvitesSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        inviteInfo = gson.fromJson(requestBody, InviteInfo.class);

        Database db = new Database();

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < inviteInfo.userIDs.length; i++) {
            int userID = Integer.parseInt(inviteInfo.userIDs[i]);
            User u = db.getUserFromDatabaseByID(userID);
            users.add(u);
        }

        int senderID = Integer.parseInt(inviteInfo.senderID);
        User sender  = db.getUserFromDatabaseByID(senderID);
        int maxGroupID = db.getMaxInviteGroupIdFromDatabase();

        Invite invite = new Invite(sender.getNickName() + " invited you to a game!", sender);
        for (User user: users) {
            response = db.addInviteToDatabase(senderID, user.getUserID(), invite.getMessage(), maxGroupID + 1);
        }

        invite.inviteUsers(users);
    }

    /**
     * Handles the response for sending invites.
     * Does the conversion from a Java class to a Json string.*
     */
    public String sendInvitesStatus() {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private class InviteInfo {
        private String senderID = "";
        private String[] userIDs = new String[0];
    }
}
