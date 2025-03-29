package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    static final UserService service = new UserService(new UserDAO(), new AuthDAO());
    static final GameService gameService = new GameService(new GameDAO(), new AuthDAO(), new UserDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        service.deleteAllUsers();
        gameService.deleteAllGames();
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        var user = service.register("joe", "joepassword", "joe@email.com");

        assertDoesNotThrow(() -> gameService.createGame(user.authToken(), "game name"));
    }

    @Test
    void createGameFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            gameService.createGame("", "game");
            throw new DataAccessException(400, "bad request");
        });
    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        var user = service.register("joe", "joepassword", "joe@email.com");
        gameService.createGame(user.authToken(), "game 1");
        gameService.createGame(user.authToken(), "game 2");
        gameService.createGame(user.authToken(), "game 3");

        assertEquals(3, gameService.listGames().size());

    }

    @Test
    void listGamesFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            gameService.listGames();
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void joinGameSuccess() throws DataAccessException {
        AuthData user = service.register("joe", "joepassword", "email@email.com");
        GameData game = gameService.createGame(user.authToken(), "game");

        assertDoesNotThrow(() -> gameService.joinGame(game.gameID(), "BLACK", user, "joe"));
    }

    @Test
    void joinGameFail() throws DataAccessException {
        AuthData user = service.register("joe", "joepassword", "joe@email.com");
        GameData game = gameService.createGame(user.authToken(), "game");
        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(1234, "BLACK", user, "joe");
            throw new DataAccessException(400, "bad request");
        });

        AuthData emptyAuthToken = new AuthData("", user.username());

        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(game.gameID(), "BLACK", emptyAuthToken, "joe");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void deleteAllGames() throws DataAccessException {
        var user = service.register("joe", "joepassword", "joe@email.com");
        gameService.createGame(user.authToken(), "game 1");
        gameService.createGame(user.authToken(), "game 2");
        gameService.createGame(user.authToken(), "game 3");

        assertEquals(3, gameService.listGames().size());
        gameService.deleteAllGames();
        assertEquals(0, gameService.listGames().size());
    }
}
