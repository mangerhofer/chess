package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameInterface {
    GameData createGame(String gameName) throws DataAccessException;
    GameData updateGame(int gameID, String whiteUsername, String blackUsername, String chessName, ChessGame chessGame) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void deleteGame(GameData game) throws DataAccessException;
    void deleteAllGames() throws DataAccessException;
}
