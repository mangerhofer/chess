package server;

import com.google.gson.*;
import dataaccess.*;
import model.*;
import service.GameService;
import service.UserService;
import spark.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Server {
    private UserDAO userDAO = new UserDAO();
    private AuthDAO authDAO = new AuthDAO();
    private GameDAO gameDAO = new GameDAO();

    private UserService userService = new UserService(userDAO, authDAO);
    private GameService gameService = new GameService(gameDAO, authDAO, userDAO);

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

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", ex.getMessage()), "status", ex.getStatusCode()));
        res.type("application/json");
        res.status(ex.getStatusCode());
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

            if (gameList.isEmpty()) {
                var games = Map.of("games", new ArrayList<>());
                res.status(200);
                return new Gson().toJson(games);
            } else {
                var games = Map.of("games", gameList);
                res.status(200);
                return new Gson().toJson(games);
            }
        }
    }

    private Object joinGame(Request req, Response res) throws DataAccessException, IllegalAccessException {
        String authToken = "";
        String reqObj = req.headers("authorization");
        var userAuths = userService.listStringAuthTokens();

        if (reqObj == null || reqObj.isEmpty() || !userAuths.contains(reqObj)) {
            throw new DataAccessException(401, "unauthorized");
        } else {
            authToken = new Gson().fromJson(reqObj, String.class);
        }

        var joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        String username = userService.getUsername(authToken);
        AuthData authData = userService.getAuthToken(authToken);

        GameData gameData = gameService.joinGame(joinGameRequest.gameID(), joinGameRequest.playerColor(), authData, username);

        res.status(200);
        return new Gson().toJson(gameData);
    }

    private Object deleteAllData(Request req, Response res) throws DataAccessException {
        userService.deleteAllUsers();
        gameService.deleteAllGames();
        res.status(200);
        return "{}";
    }
}
