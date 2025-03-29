package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    static final UserService USER_SERVICE = new UserService(new UserDAO(), new AuthDAO());
    static final GameService GAME_SERVICE = new GameService(new GameDAO(), new AuthDAO(), new UserDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        USER_SERVICE.deleteAllUsers();
        GAME_SERVICE.deleteAllGames();
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        var user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");

        assertDoesNotThrow(() -> GAME_SERVICE.createGame(user.authToken(), "game name"));
    }

    @Test
    void createGameFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            GAME_SERVICE.createGame("", "game");
            throw new DataAccessException(400, "bad request");
        });
    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        var user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        GAME_SERVICE.createGame(user.authToken(), "game 1");
        GAME_SERVICE.createGame(user.authToken(), "game 2");
        GAME_SERVICE.createGame(user.authToken(), "game 3");

        assertEquals(3, GAME_SERVICE.listGames().size());

    }

    @Test
    void listGamesFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            GAME_SERVICE.listGames();
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void joinGameSuccess() throws DataAccessException {
        AuthData user = USER_SERVICE.register("joe", "joepassword", "email@email.com");
        GameData game = GAME_SERVICE.createGame(user.authToken(), "game");

        assertDoesNotThrow(() -> GAME_SERVICE.joinGame(game.gameID(), "BLACK", user, "joe"));
    }

    @Test
    void joinGameFail() throws DataAccessException {
        AuthData user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        GameData game = GAME_SERVICE.createGame(user.authToken(), "game");
        assertThrows(DataAccessException.class, () -> {
            GAME_SERVICE.joinGame(1234, "BLACK", user, "joe");
            throw new DataAccessException(400, "bad request");
        });

        AuthData emptyAuthToken = new AuthData("", user.username());

        assertThrows(DataAccessException.class, () -> {
            GAME_SERVICE.joinGame(game.gameID(), "BLACK", emptyAuthToken, "joe");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void deleteAllGames() throws DataAccessException {
        var user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        GAME_SERVICE.createGame(user.authToken(), "game 1");
        GAME_SERVICE.createGame(user.authToken(), "game 2");
        GAME_SERVICE.createGame(user.authToken(), "game 3");

        assertEquals(3, GAME_SERVICE.listGames().size());
        GAME_SERVICE.deleteAllGames();
        assertEquals(0, GAME_SERVICE.listGames().size());
    }
}
