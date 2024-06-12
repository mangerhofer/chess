package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameInterface {
    GameData createGame(GameData game) throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    GameData getGame(GameData game) throws DataAccessException;
    void deleteGame(GameData game) throws DataAccessException;
    void deleteAllGames() throws DataAccessException;
}
