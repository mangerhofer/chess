package service;

import dataaccess.DataAccessException;
import dataaccess.GameInterface;
import model.GameData;

import java.util.Collection;

public class GameService {
    GameInterface gameInterface;

    public GameService(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameInterface.getGame(gameID);
    }

    public Collection<GameData> getAllGames() throws DataAccessException {
        return gameInterface.listGames();
    }

    public GameData createGame(String gameName) throws DataAccessException {
        return gameInterface.createGame(gameName);
    }

    public GameData updateGame(int gameID, String playerColor, String username) throws DataAccessException {
        return gameInterface.updateGame(gameID, playerColor, username);
    }

    public void deleteGame(GameData game) throws DataAccessException {
        gameInterface.deleteGame(game);
    }
}
