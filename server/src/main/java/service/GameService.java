package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameInterface;
import model.AuthData;
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

    public GameData updateGame(int gameID, ChessGame chessGame) throws DataAccessException {
        return gameInterface.updateGame(gameID, chessGame);
    }

    public GameData joinGame(int gameID, String playerColor, AuthData authToken) throws DataAccessException {
        return gameInterface.joinGame(gameID, playerColor, authToken);
    }

    public void deleteGame(GameData game) throws DataAccessException {
        gameInterface.deleteGame(game);
    }

    public void deleteAllGames() throws DataAccessException {
        gameInterface.deleteAllGames();
    }
}
