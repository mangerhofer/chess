package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {

    private GameInterface getDataAccess(Class<? extends GameInterface> databaseClass) throws DataAccessException {
        GameInterface ad;
//        if (databaseClass.equals(MemoryGameDAO.class)) {
//            ad = new SqlGameDAO();
//        } else {
            ad = new MemoryGameDAO();
//        }
        ad.deleteAllGames();
        return ad;
    }

    @ParameterizedTest
//    @ValueSource(classes = {SqlGameDAO.class, MemoryGameDAO.class})
    @ValueSource(classes = {MemoryGameDAO.class})
    void createGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);
        ChessGame chessGame = new ChessGame();

        var game = new GameData(1234, "joe", "sally", "joe v sally", chessGame);
        assertDoesNotThrow(() -> dataAccess.createGame("joe v sally"));
    }

    @ParameterizedTest
//    @ValueSource(classes = {SqlGameDAO.class, MemoryGameDAO.class})
    @ValueSource(classes = {MemoryGameDAO.class})
    void updateGame(Class<? extends GameInterface> dbClass) throws DataAccessException {
        GameInterface dataAccess = getDataAccess(dbClass);

        List<GameData> games = new ArrayList<>();
        games.add(dataAccess.createGame("joe v sally"));
        games.add(dataAccess.createGame("sally v fred"));

        int gameId1 = games.getFirst().gameID();
        int gameId2 = games.getLast().gameID();
        dataAccess.updateGame(gameId1, "BLACK", "sally");
//        dataAccess.updateGame(gameId1, "WHITE", "joe");

        assertDoesNotThrow(() -> dataAccess.updateGame(gameId1, "BLACK", "sally"));
    }

    @ParameterizedTest
//    @ValueSource(classes = {SqlGameDAO.class, MemoryGameDAO.class})
    @ValueSource(classes = {MemoryGameDAO.class})
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
//    @ValueSource(classes = {SqlGameDAO.class, MemoryGameDAO.class})
    @ValueSource(classes = {MemoryGameDAO.class})
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
//    @ValueSource(classes = {SqlGameDAO.class, MemoryGameDAO.class})
    @ValueSource(classes = {MemoryGameDAO.class})
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