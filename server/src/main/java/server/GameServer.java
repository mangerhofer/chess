//package server;
//
//import com.google.gson.Gson;
//import dataaccess.DataAccessException;
//import exception.ResponseException;
//import model.GameData;
//import service.GameService;
//import spark.*;
//
//import java.util.Map;
//
//public class GameServer {
//    private final GameService service;
//
//    public GameServer(GameService service) {
//        this.service = service;
//    }
//
//    public GameServer run(int port) {
//        Spark.port(port);
//
//        Spark.staticFiles.location("web");
//
//
//        Spark.post("/Game/:gameName", this::createGame);
//        Spark.post("/Game/:username", this::updateGame);
//        Spark.get("/Game", this::getAllGames);
//        Spark.delete("/Game/:gameID", this::deleteGame);
//        Spark.exception(ResponseException.class, this::exceptionHandler);
//
//        Spark.awaitInitialization();
//        return this;
//    }
//
//    public int port() {
//        return Spark.port();
//    }
//
//    public void stop() {
//        Spark.stop();
//    }
//
//    private void exceptionHandler(ResponseException ex, Request req, Response res) {
//        res.status(ex.StatusCode());
//    }
//
//    private Object createGame(Request req, Response res) throws DataAccessException {
//        String gameName = String.format(req.params("gameName"));
//        GameData gameData = service.createGame(gameName);
//        return new Gson().toJson(gameData);
//    }
//
//    private Object updateGame(Request req, Response res) throws DataAccessException {
//        var gameData = new Gson().fromJson(req.body(), GameData.class);
//        String username = String.format(req.params(":username"), gameData.gameName());
//        String playerColor = String.format(req.params(":playerColor"), gameData.gameName());
//        gameData = service.updateGame(gameData.gameID(),playerColor,username);
//        return new Gson().toJson(gameData);
//    }
//
//    private Object getAllGames(Request req, Response res) throws DataAccessException {
//        res.type("application/json");
//        var list = service.getAllGames().toArray();
//        return new Gson().toJson(Map.of("user", list));
//    }
//
//    private Object deleteGame(Request req, Response res) throws DataAccessException {
//        var gameID = Integer.parseInt(req.params(":gameID"));
//        GameData game = service.getGame(gameID);
//        if (game != null) {
//            service.deleteGame(game);
//            res.status(200);
//        } else {
//            res.status(401);
//        }
//        return "";
//    }
//}
