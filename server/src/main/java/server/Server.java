package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import model.GameData;
import service.UserService;
import service.ClearService;
import service.GameService;
import spark.*;

import java.util.Map;


public class Server {
    private UserService userService;
    private GameService gameService;
    private ClearService clearService;

    public Server(UserService userService, GameService gameService, ClearService clearService) {
        this.userService = userService;
        this.gameService = gameService;
        this.clearService = clearService;
    }

    public Server() {}

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.get("/user", this::getAllUsers);
        Spark.delete("/session", this::logout);
        Spark.delete("/user", this::deleteUser);

        Spark.post("/game", this::createGame);
        Spark.put("/game", this::updateGame);
        Spark.put("/game", this::joinGame);
        Spark.get("/game", this::getAllGames);
        Spark.delete("/game", this::deleteGame);

        Spark.delete("/db", this::clear);

        Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public int port() {
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    private Object register(Request req, Response res) throws DataAccessException {
        var authData = new Gson().fromJson(req.body(), AuthData.class);
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        authData = userService.register(user.username(), user.password(), user.email());
        return new Gson().toJson(authData);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        var authData = new Gson().fromJson(req.body(), AuthData.class);
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        authData = userService.login(user);
        return new Gson().toJson(authData);
    }

    private Object getAllUsers(Request req, Response res) throws DataAccessException {
        res.type("application/json");
        var list = userService.getAllUsers().toArray();
        return new Gson().toJson(Map.of("user", list));
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        var username = String.format(req.params(":authToken"));

        userService.logout(username);
        res.status(200);
        return "";
    }

    private Object deleteUser(Request req, Response res) throws DataAccessException {
        var username = String.format(req.params(":username"));
        UserData user = userService.getUser(username);
        if (user != null) {
            userService.deleteUser(user);
            res.status(200);
        } else {
            res.status(401);
        }
        return "";
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        String gameName = String.format(req.params("gameName"));
        GameData gameData = gameService.createGame(gameName);
        return new Gson().toJson(gameData);
    }

    private Object updateGame(Request req, Response res) throws DataAccessException {
        var gameData = new Gson().fromJson(req.body(), GameData.class);
        gameData = gameService.updateGame(gameData.gameID(),gameData.game());
        return new Gson().toJson(gameData);
    }

    private Object joinGame(Request req, Response res) throws DataAccessException {
        var gameData = new Gson().fromJson(req.body(), GameData.class);
        String playerColor = String.format(req.params(":playerColor"));
        AuthData authData = new Gson().fromJson(req.body(), AuthData.class);
        gameData = gameService.joinGame(gameData.gameID(), playerColor, authData);
        return new Gson().toJson(gameData);
    }

    private Object getAllGames(Request req, Response res) throws DataAccessException {
        res.type("application/json");
        var list = gameService.getAllGames().toArray();
        return new Gson().toJson(Map.of("user", list));
    }

    private Object deleteGame(Request req, Response res) throws DataAccessException {
        var gameID = Integer.parseInt(req.params(":gameID"));
        GameData game = gameService.getGame(gameID);
        if (game != null) {
            gameService.deleteGame(game);
            res.status(200);
        } else {
            res.status(401);
        }
        return "";
    }

    private Object clear(Request req, Response res) throws DataAccessException {
        clearService.clear();
        res.status(200);
        return "";
    }
}
