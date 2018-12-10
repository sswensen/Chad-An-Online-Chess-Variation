package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;


public class RemoveUsersSession {
    private UserData userData;
    private ReturnData[] usersArray;
    private int result = 0;

    /**
     * Gets all the users to return to the frontend
     *
     * @param request
     */

    public RemoveUsersSession(Request request) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        userData = gson.fromJson(requestBody, UserData.class);

        Database db = new Database();

        //get userID
        int userID = Integer.parseInt(userData.userID);

        if(db.deleteUserFromDatabase(userID)) {
            db.setAllGamesFinishedByUserID(userID);
            result = 1;
        }
    }

    /**
     * Handles the response for get users.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getResult() {
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    private class UserData {
        private String userID = "";
    }

    private class ReturnData {
        private int userID;
        private String nickname;

        private ReturnData(int _userID, String _nickname) {
            userID = _userID;
            nickname = _nickname;
        }
    }
}
