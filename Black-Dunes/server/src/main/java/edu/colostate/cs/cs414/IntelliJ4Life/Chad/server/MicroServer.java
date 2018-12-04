package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.*;
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
    get("/config", this::config);


    // client is sending data, so a HTTP POST is used instead of a GET
    post("/plan", this::plan);
    post("/login", this::login);
    post("/register", this::register);
    post("/getBoard", this::getBoard);
    post("/updateBoard", this::updateBoard);
    post("/getGames", this::getGames);
    post("/getUsers", this::getUsers);
    post("/removeUser", this::removeUser);
    post("/getValidMovesSession", this::getValidMoves);
    post("/makeMove", this::makeMove);
    post("/getProfile", this::getProfile);
    post("/getNotifications", this::getNotifications);
    post("/sendInvites", this::sendInvites);
    post("/invitationInteraction", this::invitationInteraction);

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

  /** A REST API that logs in user and returns id if user exists.
   *
   * @param request
   * @param response
   * @return
   */
  private String login(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    LoginSession lSesh = new LoginSession(request);
    Database db = new Database(lSesh.getAuthUser());
    db.getCurrentGamesFromDatabase();
    ArrayList<Game> games = db.getGames();
    for(Game g : games) {
      activeGames.add(g);
    }

    return lSesh.getUserID();

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

    return new GetGamesSession(request, activeGames).getGames();
  }

  /** A REST API that returns all of the users in the system to the frontend
   *
   * @param request
   * @param response
   * @return
   */
  private String getUsers(Request request, Response response) {
    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return new GetUsersSession(request, activeGames).getUsers();
  }

  /** A REST API that returns all of the users in the system to the frontend
   *
   * @param request
   * @param response
   * @return
   */
  private String removeUser(Request request, Response response) {
    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return new RemoveUsersSession(request).getResult();
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

  /** A REST API that returns the board to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String getBoard(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    return new GetBoardSession(request, activeGames).getBoard();
  }

  /** A REST API that returns the profile to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String getProfile(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println("getProfile");
    return new GetUsersSession(request).getUserData();
  }

  /** A REST API that returns the notifications for a user to the frontend.
   *
   * @param request
   * @param response
   * @return
   */
  private String getNotifications(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println("getNotifications");
    return new NotificationsSession(request).getNotifications();
  }


  /** A REST API that sends a game invite to a user
   *
   * @param request
   * @param response
   */
  private String sendInvites(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println("sendInvites");
    return new InvitesSession(request).sendInvitesStatus();
  }


  /** A REST API that handles invitation interactions on the front end
   *
   * @param request
   * @param response
   * @return
   */
  private String invitationInteraction(Request request, Response response) {

    response.type("text/plain");
    response.header("Access-Control-Allow-Origin", "*");

    System.out.println("invitationInteraction");
    return new GetUsersSession(request).getUserData();
  }
}
