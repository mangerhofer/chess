package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTest {
    private GameInterface getDataAccess(Class<? extends GameInterface> databaseClass) throws DataAccessException {
        GameInterface ad;

        ad = new GameDAO();

        ad.deleteAllGames();
        return ad;
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void createGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);
        ChessGame chessGame = new ChessGame();

        var game = new GameData(1234, "joe", "sally", "joe v sally", chessGame);
        assertDoesNotThrow(() -> dataAccess.createGame("joe v sally"));
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void updateGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);

        List<GameData> games = new ArrayList<>();
        games.add(dataAccess.createGame("joe v sally"));
        games.add(dataAccess.createGame("sally v fred"));

        int gameId1 = games.getFirst().gameID();
        ChessGame chessGame = new ChessGame();

        dataAccess.updateGame(gameId1, chessGame);

        assertDoesNotThrow(() -> dataAccess.updateGame(gameId1, chessGame));
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void joinGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);
        AuthInterface auth = new AuthDAO();

        List<GameData> games = new ArrayList<>();
        games.add(dataAccess.createGame("joe v sally"));

        UserData user = new UserData("joe", "joepassword", "joe@email.com");
        AuthData authToken = auth.createAuthToken(user, user.username(), user.password());

        int gameId1 = games.getFirst().gameID();
        dataAccess.joinGame(gameId1, "WHITE", authToken, user.username());

        assertDoesNotThrow(() -> dataAccess.joinGame(gameId1, "BLACK", authToken, "joe"));
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void listGames(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);

        List<GameData> expected = new ArrayList<>();
        expected.add(dataAccess.createGame("joe v sally"));
        expected.add(dataAccess.createGame("sally v fred"));
        expected.add(dataAccess.createGame("fred v joe"));

        var actual = dataAccess.listGames();
        assertGameCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void deleteGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);

        List<GameData> expected = new ArrayList<>();
        var deleteGame = dataAccess.createGame("joe v sally");
        expected.add(dataAccess.createGame("sally v fred"));
        expected.add(dataAccess.createGame("fred v joe"));

        dataAccess.deleteGame(deleteGame);

        var actual = dataAccess.listGames();
        assertGameCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {GameDAO.class})
    void deleteAllGames(Class<? extends GameInterface> dbClass) throws Exception {
        GameInterface dataAccess = getDataAccess(dbClass);

        dataAccess.createGame("joe v sally");
        dataAccess.createGame("sally v fred");

        dataAccess.deleteAllGames();

        var actual = dataAccess.listGames();
        assertEquals(0, actual.size());
    }


    public static void assertGameEqual(GameData expected, GameData actual) {
        assertEquals(expected.gameID(), actual.gameID());
        assertEquals(expected.whiteUsername(), actual.whiteUsername());
        assertEquals(expected.blackUsername(), actual.blackUsername());
        assertEquals(expected.gameName(), actual.gameName());
        assertEquals(expected.game(), actual.game());
    }

    public static void assertGameCollectionEqual(Collection<GameData> expected, Collection<GameData> actual) {
        GameData[] actualList = actual.toArray(new GameData[]{});
        GameData[] expectedList = expected.toArray(new GameData[]{});
        assertEquals(expectedList.length, actualList.length);
        for (var i = 0; i < actualList.length; i++) {
            assertGameEqual(expectedList[i], actualList[i]);
        }
    }
}
