package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

public class LoginSession {
    private LoginUser user;
    private User authUser;

    /**
     * Handles trip planning request, creating a new trip object from the trip request.
     * Does the conversion from Json to a Java class before planning the trip.
     *
     * @param request
     */

    public LoginSession(Request request) {
        // first print the request
//        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());
        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        user = gson.fromJson(requestBody, LoginUser.class);
        // plan the trip.
        // distance.plan();

        // TODO convert LoginUser to a User after authenticating and verifying in the database.

        Database db = new Database();
        authUser = db.getUserFromDatabase(user.username, user.password);

        // log something.
//        System.out.println("UserID: " + user.getUserID());
//        System.out.println("Username: " + user.getUsername());
//        System.out.println("Email: " + user.getEmail());


    }

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    /**
     * Handles the response for a User object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUserGson() {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    /**
     * Handles the response for a UserID object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUserID() {
        Gson gson = new Gson();
        return gson.toJson(authUser.getUserID());
    }

    private class LoginUser {
        private String username = "";
        private String password = "";
    }
}
