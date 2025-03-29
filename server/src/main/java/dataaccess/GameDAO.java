package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.ListGameResult;
import model.GameData;

import java.util.*;

public class GameDAO implements GameInterface {
    final private LinkedHashMap<Integer, GameData> games = new LinkedHashMap<>();
    final private LinkedHashMap<Integer, ListGameResult> listGames = new LinkedHashMap<>();

    public GameData createGame(String gameName) {
        ChessGame chessGame = new ChessGame();
        Random rand = new Random();
        int max = 9999, min = 1000;
        int gameID = rand.nextInt(max - min +1) + min;

        GameData game = new GameData(gameID, null, null, gameName, chessGame);
        ListGameResult createGameResult = new ListGameResult(gameID, null, null, gameName);
        games.put(gameID, game);
        listGames.put(gameID, createGameResult);

        return game;
    }

    public GameData updateGame(int gameID, ChessGame chessGame) {
        GameData game = games.get(gameID);
        GameData fake = null;

        games.replace(gameID, game, fake);

        if (!Objects.equals(chessGame, game.game())) {
            game = game.updateGame(chessGame);
        }

        games.replace(gameID, fake, game);

        return game;
    }

    @Override
    public GameData joinGame(int gameID, String playerColor, AuthData authToken, String username) throws DataAccessException {
        GameData game = games.get(gameID);
        ListGameResult gameResult = listGames.get(gameID);

        games.replace(gameID, game, null);
        listGames.replace(gameID, gameResult, null);

        if (playerColor == null) {
            throw new DataAccessException(400, "bad request");
        } else {
            if (playerColor.equals("BLACK")) {
                if (game.blackUsername() == null) {
                    game = game.setBlackUsername(username);
                    gameResult = gameResult.setBlackUsername(username);
                } else {
                    throw new DataAccessException(403, "already taken");
                }
            } else if (playerColor.equals("WHITE")) {
                if (game.whiteUsername() == null) {
                    game = game.setWhiteUsername(username);
                    gameResult = gameResult.setWhiteUsername(username);
                } else {
                    throw new DataAccessException(403, "already taken");
                }
            } else {
                throw new DataAccessException(400, "bad request");
            }
        }

        games.replace(gameID, null, game);
        listGames.replace(gameID, null, gameResult);

        return game;
    }

    public Collection<GameData> listGames() {
        return games.values();
    }

    public Collection<ListGameResult> listGameResults() {
        return listGames.values();
    }

    public void deleteGame(GameData game) {
        int gameValue = 0;

        for (Map.Entry<Integer, GameData> possGame : games.entrySet()) {
            if (possGame.getValue().equals(game)) {
                gameValue = possGame.getKey();
            }
        }

        games.remove(gameValue);
    }

    public void deleteAllGames() {
        games.clear();
        listGames.clear();
    }
}
