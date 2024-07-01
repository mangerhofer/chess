package service;

import chess.ChessGame;
import dataaccess.AuthInterface;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    static final GameService service = new GameService(new MemoryGameDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        service.deleteAllGames();
    }

    @Test
    void createGame() throws DataAccessException {
        ChessGame chess = new ChessGame();
        var game = new GameData(1234, "joe", "sally", "joe v sally", chess);
        game = service.createGame("joe v sally");

        var games = service.getAllGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(game));
    }

    @Test
    void getAllGames() throws DataAccessException {
        List<GameData> expected = new ArrayList<>();
        expected.add(service.createGame("joe v sally"));
        expected.add(service.createGame("sally v fred"));
        expected.add(service.createGame("fred v joe"));

        var actual = service.getAllGames();
        assertIterableEquals(expected, actual);
    }

    @Test
    void updateGame() throws DataAccessException {
        List<GameData> expected = new ArrayList<>();
        GameData game1 = service.createGame("joe v sally");
        GameData game2 = service.createGame("sally v fred");
        GameData game3 = service.createGame("fred v joe");

        ChessGame chess = new ChessGame();

        game1 = service.updateGame(game1.gameID(), game1.game());
        game2 = service.updateGame(game2.gameID(), chess);
//        game3 = service.updateGame(game3.gameID(), "fred", "sally", game3.gameName(), game3.game());


        expected.add(game1);
        expected.add(game2);
        expected.add(game3);

        var actual = service.getAllGames();
        assertIterableEquals(expected, actual);
    }

    @Test
    void joinGame() throws DataAccessException {
        List<GameData> expected = new ArrayList<>();
        GameData game1 = service.createGame("joe v sally");
        GameData game2 = service.createGame("sally v fred");

        AuthInterface auth = new MemoryAuthDAO();
        UserData user = new UserData("joe", "joepassword", "joe@email.com");
        AuthData authToken = auth.createAuthToken(user, user.username(), user.password());


        game1 = service.joinGame(game1.gameID(), "BLACK", authToken);
        game2 = service.joinGame(game2.gameID(), null, authToken);


        expected.add(game1);
        expected.add(game2);

        var actual = service.getAllGames();
        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteGame() throws DataAccessException {
        List<GameData> expected = new ArrayList<>();
        var game1 = service.createGame("joe v sally");
        expected.add(service.createGame("sally v fred"));
        expected.add(service.createGame("fred v joe"));

        service.deleteGame(game1);
        var actual = service.getAllGames();
        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteAllGames() throws DataAccessException {
        service.createGame("joe v sally");
        service.createGame("sally v fred");
        service.createGame("fred v joe");

        service.deleteAllGames();
        assertEquals(0, service.getAllGames().size());
    }
}
