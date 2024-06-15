//package service;
//
//import chess.ChessGame;
//import dataaccess.MemoryUserDAO;
//import dataaccess.MemoryAuthDAO;
//import dataaccess.MemoryGameDAO;
//import model.GameData;
//import model.UserData;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import server.Server;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ServerTest {
//    static private Server server;
//    static private UserService userService;
//    static private GameService gameService;
//    static private ClearService clearService;
//
//    @BeforeAll
//    static void startServer() {
//        var userService = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());
//        var gameService = new GameService(new MemoryGameDAO());
//        var clearService = new ClearService(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
//        server = new Server(userService, gameService, clearService);
//        server.run(0);
//        var url = "http://localhost:" + server.port();
//    }
//
//    @AfterAll
//    static void stopServer() {
//        server.stop();
//    }
//
//    @BeforeEach
//    void clear() {
//        assertDoesNotThrow(() -> server.clear());
//    }
//
//    @Test
//    void register() {
//        var joe = new UserData("joe", "joepassword", "joe@email.com");
//        UserData result = assertDoesNotThrow(() -> server.register(joe));
//        assertUserEqual(joe, result);
//    }
//
//    @Test
//    void login() {
//        var joe = new UserData("joe", "joepassword", "joe@email.com");
//        UserData result = assertDoesNotThrow(() -> server.login(joe));
//        assertUserEqual(joe, result);
//    }
//
//    @Test
//    void getAllUsers() throws Exception {
//        var expected = new ArrayList<UserData>();
//        expected.add(server.register(new UserData("joe", "joepassword", "joe@email.com")));
//        expected.add(server.register(new UserData("sally", "sallypassord", "sally@email.com")));
//
//        UserData result = assertDoesNotThrow(() -> server.getAllUsers());
//        assertUserCollectionEqual(expected, List.of(result));
//    }
//
//    @Test
//    void logout() throws Exception {
//        var expected = new ArrayList<UserData>();
//        expected.add(server.register(new UserData("sally", "sallypassword", "sally@email.com")));
//
//        var joe = server.register(new UserData("joe", "joepassword", "joe@email.com"));
//        server.logout("joe");
//
//        UserData result = assertDoesNotThrow(() -> server.getAllUsers());
//        assertUserCollectionEqual(expected, List.of(result));
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        var expected = new ArrayList<UserData>();
//        expected.add(server.register(new UserData("sally", "sallypassword", "sally@email.com")));
//
//        var joe = server.register(new UserData("joe", "joepassword", "joe@email.com"));
//        server.deleteUser(joe);
//
//        UserData result = assertDoesNotThrow(() -> server.getAllUsers());
//        assertUserCollectionEqual(expected, List.of(result));
//    }
//
//    @Test
//    void createGame() throws Exception {
//        ChessGame chess = new ChessGame();
//        var game = new GameData(1234, "joe", "sally", "joe v sally", chess);
//        GameData result = assertDoesNotThrow(() -> server.createGame("joe v sally"));
//        assertGameEqual(game, result);
//    }
//
//    @Test
//    void updateGame() throws Exception {
//        ChessGame chess = new ChessGame();
//        var game = new GameData(1234, "joe", "sally", "joe v sally", chess);
//        GameData result = assertDoesNotThrow(() -> server.updateGame(1234, "WHITE", "joe"));
//        assertGameEqual(game, result);
//    }
//
//    @Test
//    void getAllGames() throws Exception {
//        var expected = new ArrayList<GameData>();
//        ChessGame chess = new ChessGame();
//        ChessGame chess2 = new ChessGame();
//        expected.add(server.createGame(new GameData(1234, "joe", "sally", "joe v sally", chess)));
//        expected.add(server.createGame(new GameData(1234, "sally", "fred", "sally v fred", chess2)));
//
//        GameData result = assertDoesNotThrow(() -> server.getAllGames());
//        assertGameCollectionEqual(expected, List.of(result));
//    }
//
//    @Test
//    void deleteGame() throws Exception {
//        var expected = new ArrayList<GameData>();
//        ChessGame chess = new ChessGame();
//        ChessGame chess2 = new ChessGame();
//        expected.add(server.createGame(new GameData(1234, "sally", "fred", "sally v fred", chess2)));
//
//        var joe = server.createGame(new GameData(1234, "joe", "sally", "joe v sally", chess));
//        server.deleteGame(joe);
//
//        GameData result = assertDoesNotThrow(() -> server.getAllGames());
//        assertGameCollectionEqual(expected, List.of(result));
//    }
//
//    public static void assertUserEqual(UserData expected, UserData actual) {
//        assertEquals(expected.username(), actual.username());
//        assertEquals(expected.password(), actual.password());
//        assertEquals(expected.email(), actual.email());
//    }
//
//    public static void assertUserCollectionEqual(Collection<UserData> expected, Collection<UserData> actual) {
//        UserData[] actualList = actual.toArray(new UserData[]{});
//        UserData[] expectedList = expected.toArray(new UserData[]{});
//        assertEquals(expectedList.length, actualList.length);
//        for (var i = 0; i < actualList.length; i++) {
//            assertUserEqual(expectedList[i], actualList[i]);
//        }
//    }
//
//    public static void assertGameEqual(GameData expected, GameData actual) {
//        assertEquals(expected.gameID(), actual.gameID());
//        assertEquals(expected.whiteUsername(), actual.whiteUsername());
//        assertEquals(expected.blackUsername(), actual.blackUsername());
//        assertEquals(expected.gameName(), actual.gameName());
//        assertEquals(expected.game(), actual.game());
//    }
//
//    public static void assertGameCollectionEqual(Collection<GameData> expected, Collection<GameData> actual) {
//        GameData[] actualList = actual.toArray(new GameData[]{});
//        GameData[] expectedList = expected.toArray(new GameData[]{});
//        assertEquals(expectedList.length, actualList.length);
//        for (var i = 0; i < actualList.length; i++) {
//            assertGameEqual(expectedList[i], actualList[i]);
//        }
//    }
//}