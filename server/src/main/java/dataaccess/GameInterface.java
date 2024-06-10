package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public interface GameInterface {
    public Collection<GameData> userGames = new ArrayList<>();
    GameData createGame(GameData game) throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void deleteGame(GameData game) throws DataAccessException;
    void deleteAllGames() throws DataAccessException;
}
