package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.GetBoardSession;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.LoginSession;

import com.google.gson.Gson;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.*;
import org.json.*;
import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.User;

import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.ArrayList;

import static spark.Spark.*;


/** A simple micro-server for the web.  Just what we need, nothing more.
 *
 */
public class MicroServer {

  private int    port;
  private String name;
  private String path = "/public/";
  private ActiveGames activeGames;

  /** Creates a micro-server to load static files and provide REST APIs.
   *
   * @param port Which port to start the server on
   * @param name Name of the server
   */
  MicroServer(int port, String name, ActiveGames activeGames) {
    this.port = port;
    this.name = name;
    this.activeGames = activeGames;

    port(port);

    // serve the static files: index.html and bundle.js
    Spark.staticFileLocation(this.path);
    get("/", (req, res) -> {res.redirect("index.html"); return null;});

    // register all micro-services and the function that services them.
    // start with HTTP GET
    get("/about", this::about);
    get("/echo", this::echo);
    get("/hello/:name", this::hello);
    get("/team", this::team);
    get("/board", this::getBoard);


    // client is sending data, so a HTTP POST is used instead of a GET
    get("/config", this::config);
    post("/plan", this::plan);
    post("/login", this::login);
    post("/register", this::register);
    post("/getBoard", this::getBoard);
    post("/updateBoard", this::updateBoard);
    post("/getGames", this::getGames);
    post("/getValidMovesSession", this::getValidMoves);
    post("/makeMove", this::makeMove);

    System.out.println("\n\nServer running on port: " + this.port + "\n\n");
  }

  /** A REST API that describes the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String about(Request request, Response response) {

    response.type("text/html");
    response.header("Access-Control-Allow-Origin", "*");

    return "<html><head></head><body><h1>"+name+" Micro-server on port "+port+"</h1></body></html>";
  }

  /** A REST API that returns the current server configuration
   *
   * @param request
   * @param response
   * @return
   */
  private String config(Request request, Response response) {
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");

    return Config.getConfig();
  }

  /** A REST API that echos the client request.
   *
   * @param request
   * @param response
   * @return
   */
  private String echo(Request request, Response response) {

    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");

    return HTTP.echoRequest(request);
  }

  /** A REST API demonstrating the use of a parameter.
   *
   * @param request
   * @param response
   * @return
   */
  private String hello(Request request, Response response) {

    response.type("text/html");
    response.header("Access-Control-Allow-Origin", "*");

    return Greeting.html(request.params(":name"));
  }


  /** A REST API to support trip planning.
   *
   * @param request
   * @param response
   * @return
   */
  private String plan(Request request, Response response) {

    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");

    return "{}";
  }

  /** A REST API that returns the team information associated with the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String team(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return name;
  }

  /** A REST API that returns the team information associated with the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String login(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println();
    LoginSession lSesh = new LoginSession(request);
    Database db = new Database(lSesh.getAuthUser());
    db.getCurrentGamesFromDatabase();
    ArrayList<Game> games = db.getGames();
  for(Game g : games) {
      activeGames.add(g);
  }

    return lSesh.getUserID(); // Send back user id, THIS IS INSECURE

  }

  /** A REST API that registers a user in the database.
   *
   * @param request
   * @param response
   * @return
   */
  private String register(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println();
    return new RegisterSession(request).getUserID(); // Send back user id, THIS IS INSECURE
  }

  /** A REST API that updates the board in the database.
   *
   * @param request
   * @param response
   * @return
   */
  private String updateBoard(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    //get user for auth at some point
//    JSONObject userObj = new JSONObject(request.params(":user"));
//    User user = new User(userObj.getInt("userID"), userObj.getString("username"),
//            userObj.getString("nickName"), userObj.getString("email"));

    //connect to db and update game
    Database db = new Database();
    db.updateGameInDatabase(Integer.parseInt(request.params(":gameID")),
            request.params(":gameBoard"), Integer.parseInt(request.params(":turn")));

    //TODO: Needs return value at some point
    return "";
  }

  /** A REST API that returns the a user's games to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String getGames(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");


    Gson gson = new Gson();
    //get userID
    System.out.println("body: " + request.body());
    int userID = Integer.parseInt(request.body());
    System.out.println("userID: " +userID);
    //connect to db and update game
    Database db = new Database();
    //get user
    User user = db.getUserFromDatabaseByID(userID);
    if(user != null) {
      System.out.println("user.userID: " + user.getUserID());
      System.out.println();
      db.setUser(user);
      db.getCurrentGamesFromDatabase();
      ArrayList<Game> games = db.getGames();
      System.out.println(gson.toJson(games));
      return gson.toJson(games);
    }
    return "";
  }

  /** A REST API that returns the team information associated with the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String getValidMoves(Request request, Response response) {
    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return new GetValidMovesSession(request, activeGames).getValidMoves();
  }

  /** A REST API that makes a move in the backend and sends the result to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String makeMove(Request request, Response response) {
    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return new MakeMoveSession(request, activeGames).getMakeMove();
  }

  /** A REST API that creates a new game and adds it to activeGames and the database.
   *
   * @param request
   * @param response
   * @return
   */
  private String createGame(Request request, Response response) {
    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return "";
  }

  /** A REST API that returns the board to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String getBoard(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println();
    return new GetBoardSession(request, activeGames).getBoard();
  }
}
