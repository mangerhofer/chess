package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.*;

import java.util.Map;

public class Server {
    private final UserService userService;

    public Server(UserService userService) {
        this.userService = userService;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
//        Spark.get("/game", this::listGames);
//        Spark.post("/game", this::createGame);
//        Spark.put("/game", this::joinGame);
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

    private Object deleteAllData(Request req, Response res) throws DataAccessException {
        userService.deleteAllUsers();
        res.status(200);
        return "";
    }
}
