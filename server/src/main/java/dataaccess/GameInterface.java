package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.AuthData;
import model.ListGameResult;

import java.util.Collection;

public interface GameInterface {
    GameData createGame(String gameName) throws DataAccessException;
    GameData updateGame(int gameID, ChessGame chessGame) throws DataAccessException;
    GameData joinGame(int gameID, String playerColor, AuthData authToken, String username) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    Collection<ListGameResult> listGameResults() throws DataAccessException;
    void deleteGame(GameData game) throws DataAccessException;
    void deleteAllGames() throws DataAccessException;
}
