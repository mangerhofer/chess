//package server;
//
//import dataaccess.DataAccessException;
//import exception.ResponseException;
//import service.ClearService;
//import spark.*;
//
//public class ClearServer {
//    private ClearService service;
//
//    public ClearServer(ClearService service) {
//        this.service = service;
//    }
//
//    public ClearServer run(int port) {
//        Spark.port(port);
//
//        Spark.staticFiles.location("web");
//
//
//        Spark.delete("/Clear", this::clear);
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
//    private Object clear(Request req, Response res) throws DataAccessException {
//        service.clear();
//        res.status(200);
//        return "";
//    }
//}
