package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotificationsSession {
    private NotificationTypeRequest notificationTypeRequest;
    private ArrayList<Database.NotificationRow> response;

    /**
     * Handles returning the board and the turn to the frontend
     *
     * @param request
     */

    public NotificationsSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        notificationTypeRequest = gson.fromJson(requestBody, NotificationTypeRequest.class);

        Database db = new Database();

        ArrayList<Database.NotificationRow> notificationRows = db.getNotificationsFromDatabase(notificationTypeRequest.userID);
        response = new ArrayList<>();

        if (notificationTypeRequest.notificationType.equals("notification")) {
            for (Database.NotificationRow row: notificationRows) {
                if (row.user2ID == -1) {
                    response.add(row);
                }
            }
        } else if (notificationTypeRequest.notificationType.equals("invitation")) {
            for (Database.NotificationRow row: notificationRows) {
                if (row.user2ID > -1) {
                    response.add(row);
                }
            }
        }
    }

    /**
     * Handles the response for sending invites.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getNotifications() {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private class NotificationTypeRequest {
        private String notificationType = "";
        private int userID = -1;
    }
}


