package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.*;

public class GameDAO implements GameInterface {
    final private LinkedHashMap<Integer, GameData> games = new LinkedHashMap<>();

    public GameData createGame(String gameName) {
        ChessGame chessGame = new ChessGame();
        Random rand = new Random();
        int max = 9999, min = 1000;
        int gameID = rand.nextInt(max - min +1) + min;

        GameData game = new GameData(gameID, null, null, gameName, chessGame);
        games.put(gameID, game);

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
    public GameData joinGame(int gameID, String playerColor, AuthData authToken) throws DataAccessException {
        GameData game = games.get(gameID);

        games.replace(gameID, game, null);

        String username = authToken.username();
        if (Objects.equals(playerColor, "BLACK")) {
            game = game.setBlackUsername(username);
        } else if (Objects.equals(playerColor, "WHITE")) {
            game = game.setWhiteUsername(username);
        }

        games.replace(gameID, null, game);

        return game;
    }

    public Collection<GameData> listGames() {
        return games.values();
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
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
    }
}
