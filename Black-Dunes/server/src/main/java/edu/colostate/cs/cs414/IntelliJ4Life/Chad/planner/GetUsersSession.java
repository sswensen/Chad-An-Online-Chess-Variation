package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;


public class GetUsersSession {
    private UserData userData;
    private ReturnData[] usersArray;

    /**
     * Gets all the users to return to the frontend
     *
     * @param request
     */

    public GetUsersSession(Request request, ActiveGames activeGames) {
        // first print the request
        //System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        userData = gson.fromJson(requestBody, UserData.class);

        Database db = new Database();

        //get userID
        int userID = Integer.parseInt(userData.userID);

        ArrayList<User> users = db.getAllUsersFromDatabase();
        usersArray = new ReturnData[users.size() - 1];
        int i = 0;

        // Convert users ArrayList to array removing the current user
        for (User u : users) {
            if (u.getUserID() != userID) {
                usersArray[i] = new ReturnData(u.getUserID(), u.getNickName());

                i++;
            }
        }
    }

    /**
     * Handles the response for get users.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUsers() {
        Gson gson = new Gson();
        return gson.toJson(usersArray);
    }

    private class UserData {
        private String userID = "";
    }

    private class ReturnData {
        private int userID;
        private String nickname;

        private ReturnData(int _userID, String _nickname){
            userID = _userID;
            nickname = _nickname;
        }
    }
}
