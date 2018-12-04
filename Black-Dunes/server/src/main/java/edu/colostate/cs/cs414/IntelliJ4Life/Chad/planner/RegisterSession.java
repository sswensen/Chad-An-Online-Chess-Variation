package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import org.json.JSONObject;
import spark.Request;

public class RegisterSession {
    private RegisterUser user;
    private User newUser;

    /**
     * Registers a user into the database
     * @param request
     */

    public RegisterSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());
        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        user = gson.fromJson(requestBody, RegisterUser.class);

        Database db = new Database();
        boolean registered = db.registerUserInDatabase(user.username, user.nickname, user.email, user.password);
        newUser = db.getUserFromDatabase(user.username, user.password);
    }

    /**
     * Handles the response for a User object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUser() {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    /**
     * Handles the response for a UserID object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUserID() {
        Gson gson = new Gson();
        return gson.toJson(newUser.getUserID());
    }

    private class RegisterUser {
        private String username = "";
        private String password = "";
        private String email = "";
        private String nickname = "";
    }
}
