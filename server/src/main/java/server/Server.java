package server;

import io.javalin.*;
import io.javalin.http.Context;
import com.google.gson.*;
import dataaccess.*;
import model.*;
import service.GameService;
import service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class Server {

    private UserDAO userDAO = new UserDAO();
    private AuthDAO authDAO = new AuthDAO();
    private GameDAO gameDAO = new GameDAO();

    private UserService userService = new UserService(userDAO, authDAO);
    private GameService gameService = new GameService(gameDAO, authDAO, userDAO);

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user", this::register);
        javalin.post("/session", this::login);
        javalin.delete("/session", this::logout);
        javalin.get("/game", this::listGames);
        javalin.post("/game", this::createGame);
        javalin.put("/game", this::joinGame);
        javalin.exception(DataAccessException.class, this::exceptionHandler);
        javalin.delete("/db", this::deleteAllData);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void exceptionHandler(DataAccessException ex, Context ctx) {
        ctx.status(ex.getStatusCode());
        ctx.json(ex.toJson());
    }

    private void register(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        var users = userService.listUsers();

        if (user.username() == null || user.password() == null || user.email() == null) {
            ctx.status(400);
            throw new DataAccessException(400, "Error: bad request, fields empty");
        } else if (!users.contains(user)) {
            ctx.status(200);
            var registerResult = userService.register(user.username(), user.password(), user.email());
            ctx.json(new Gson().toJson(registerResult));
        } else {
            ctx.status(403);
            throw new DataAccessException(403, "Error: username already taken");
        }
    }

    private void login(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);

        var authData = userService.login(user.username(), user.password());
        ctx.status(200);
        ctx.json(new Gson().toJson(authData));
    }

    private void logout(Context ctx) throws DataAccessException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        userService.logout(authToken);

        ctx.status(200);
    }

    private void createGame(Context ctx) throws DataAccessException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        JsonObject body = new Gson().fromJson(ctx.body(), JsonObject.class);
        if (body == null || body.isEmpty()) {
            throw new DataAccessException(400, "Error: unauthorized");
        }
        String gameName = body.get("gameName").getAsString();

        var userAuths = userService.listStringAuthTokens();

        if (!userAuths.contains(authToken)) {
            throw new DataAccessException(401, "Error: unauthorized");
        } else {

            var gameID = gameService.createGame(authToken, gameName);

            ctx.status(200);
            ctx.json(new Gson().toJson(gameID));
        }
    }

    private void listGames(Context ctx) throws DataAccessException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);

        var userAuths = userService.listStringAuthTokens();

        if (!userAuths.contains(authToken)) {
            throw new DataAccessException(401, "Error: unauthorized");
        } else {
            Collection<ListGameResult> gameList = gameService.listGames();

            if (gameList.isEmpty()) {
                var games = Map.of("games", new ArrayList<>());
                ctx.status(200);
                ctx.json(new Gson().toJson(games));
            } else {
                var games = Map.of("games", gameList);
                ctx.status(200);
                ctx.json(new Gson().toJson(games));
            }
        }
    }

    private void joinGame(Context ctx) throws DataAccessException, IllegalAccessException {
        String authToken = "";
        String reqObj = ctx.header("authorization");
        var userAuths = userService.listStringAuthTokens();

        if (reqObj == null || reqObj.isEmpty() || !userAuths.contains(reqObj)) {
            throw new DataAccessException(401, "Error: unauthorized");
        } else {
            authToken = new Gson().fromJson(reqObj, String.class);
        }

        var joinGameRequest = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
        String username = userService.getUsername(authToken);
        AuthData authData = userService.getAuthToken(authToken);

        GameData gameData = gameService.joinGame(joinGameRequest.gameID(), joinGameRequest.playerColor(), authData, username);

        ctx.status(200);
        ctx.json(new Gson().toJson(gameData));
    }

    private void deleteAllData(Context ctx) throws DataAccessException {
        userService.deleteAllUsers();
        gameService.deleteAllGames();
        ctx.status(200);
    }
}
