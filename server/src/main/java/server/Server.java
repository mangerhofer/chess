package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.AuthInterface;
import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import model.*;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Server {
    private final UserService userService;
    private final GameService gameService;

    public Server(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.exception(DataAccessException.class, this::exceptionHandler);
        Spark.delete("/db", this::deleteAllData);

        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", ex.getMessage()), "status", ex.StatusCode()));
        res.type("application/json");
        res.status(ex.StatusCode());
        res.body(body);
    }

    private Object register(Request req, Response res) throws DataAccessException {
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        var users = userService.listUsers();

        if (user.username() == null || user.password() == null || user.email() == null) {
            res.status(400);
            throw new DataAccessException(400, "bad request, fields empty");
        } else if (!users.contains(user)) {
            res.status(200);
            return userService.register(user.username(), user.password(), user.email());
        } else {
            res.status(403);
            throw new DataAccessException(403, "username already taken");
        }
    }

    private Object login(Request req, Response res) throws DataAccessException {
        UserData user = new Gson().fromJson(req.body(), UserData.class);

        var authData = userService.login(user.username(), user.password());
        res.status(200);
        return new Gson().toJson(authData);
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
        userService.logout(authToken);

        res.status(200);
        return "";
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
        JsonObject body = new Gson().fromJson(req.body(), JsonObject.class);
        String gameName = body.get("gameName").getAsString();

        var userAuths = userService.listStringAuthTokens();

        if (!userAuths.contains(authToken)) {
            throw new DataAccessException(401, "unauthorized");
        } else {

            var gameID = gameService.createGame(authToken, gameName);

            res.status(200);
            return new Gson().toJson(gameID);
        }
    }

    private Object listGames(Request req, Response res) throws DataAccessException {
        String authToken = new Gson().fromJson(req.headers("authorization"), String.class);

        var userAuths = userService.listStringAuthTokens();

        if (!userAuths.contains(authToken)) {
            throw new DataAccessException(401, "unauthorized");
        } else {
            Collection<ListGameResult> gameList = gameService.listGames();

            res.status(200);
            return new Gson().toJson(gameList);
        }
    }

    private Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
        var joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        String username = userService.getUsername(authToken);

        var userAuths = userService.listStringAuthTokens();
        if (!userAuths.contains(authToken)) {
            throw new DataAccessException(401, "unauthorized");
        } else {
            GameData gameData = gameService.joinGame(joinGameRequest.gameID(), joinGameRequest.playerColor(), authToken, username);

            String gameDataString = gameData.toString();
//            var obj = Map.of(gameDataString.);
//            var serializer = new Gson();
//            var gsonObj = serializer.fromJson(gameDataString, String.class);

            res.status(200);
//            return new Gson().toJson(gameDataString);
            return JsonParser.parseString(gameDataString).getAsJsonObject();
//            return gsonObj;
        }

    }

    private Object deleteAllData(Request req, Response res) throws DataAccessException {
        userService.deleteAllUsers();
        gameService.deleteAllGames();
        res.status(200);
        return "";
    }
}
