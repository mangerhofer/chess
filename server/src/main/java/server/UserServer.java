package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.*;

import java.util.Map;

public class UserServer {
    private final UserService service;

    public UserServer(UserService service) {
        this.service = service;
    }

    public UserServer run(int port) {
        Spark.port(port);

        Spark.staticFiles.location("public");


        Spark.post("/User", this::register);
        Spark.get("/User", this::login);
        Spark.get("/User", this::getAllUsers);
        Spark.delete("/User/:username", this::logout);
        Spark.delete("/User/:username", this::deleteUser);
        Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return this;
    }

    public int port() {
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    private Object register(Request req, Response res) throws DataAccessException {
        var authData = new Gson().fromJson(req.body(), AuthData.class);
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        authData = service.register(user.username(), user.password(), user.email());
        return new Gson().toJson(authData);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        var authData = new Gson().fromJson(req.body(), AuthData.class);
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        authData = service.login(user);
        return new Gson().toJson(authData);
    }

    private Object getAllUsers(Request req, Response res) throws DataAccessException {
        res.type("application/json");
        var list = service.getAllUsers().toArray();
        return new Gson().toJson(Map.of("user", list));
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        var username = String.format(req.params(":username"));

        service.logout(username);
        res.status(200);
        return "";
    }

    private Object deleteUser(Request req, Response res) throws DataAccessException {
        var username = String.format(req.params(":username"));
        UserData user = service.getUser(username);
        if (user != null) {
            service.deleteUser(user);
            res.status(200);
        } else {
            res.status(401);
        }
        return "";
    }
}
