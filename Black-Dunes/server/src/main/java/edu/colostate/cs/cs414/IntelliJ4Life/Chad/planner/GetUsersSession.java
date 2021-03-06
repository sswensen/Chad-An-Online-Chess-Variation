package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.ActiveGames;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.Database;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.server.HTTP;
import spark.Request;

import java.util.ArrayList;


public class GetUsersSession {
    private UserData userData;
    private ReturnData[] usersArray;
    private UserReturnData returnUserData;

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
        for(User u : users) {
            if(u.getUserID() != userID) {
                usersArray[i] = new ReturnData(u.getUserID(), u.getNickName());

                i++;
            }
        }
    }

    public GetUsersSession(Request request) {
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

        User tempuser = db.getUserFromDatabaseByID(userID);
        User.Profile profile = tempuser.new Profile();
        returnUserData = new UserReturnData(tempuser.getUserID(), tempuser.getNickName(), tempuser.getEmail(), profile.getWins(), profile.getLosses());
    }

    /**
     * Handles the response for get users.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getUsers() {
        Gson gson = new Gson();
        return gson.toJson(usersArray);
    }

    public String getUserData() {
        Gson gson = new Gson();
        return gson.toJson(returnUserData);
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

    private class UserReturnData {
        private int userID;
        private String nickname;
        private String email;
        private int wins;
        private int losses;

        private UserReturnData(int userID, String nickname, String email, int wins, int losses) {
            this.userID = userID;
            this.nickname = nickname;
            this.email = email;
            this.wins = wins;
            this.losses = losses;
        }

    }
}
