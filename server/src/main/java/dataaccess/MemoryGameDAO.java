package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameInterface {
    final private LinkedHashMap<Integer, GameData> games = new LinkedHashMap<>();

    public GameData createGame(String gameName) {
        ChessGame chessGame = new ChessGame();
        Random rand = new Random();
        int max = 9999, min = 1000;
        int gameID = rand.nextInt(max - min + 1) + min;

        GameData game = new GameData(gameID, null, null, gameName, chessGame);

        games.put(gameID, game);

        return game;
    }

    public GameData updateGame(int gameID, String playerColor, String username) {
//    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
//                  This is used when players join a game or when a move is made.
        GameData game = games.get(gameID);
        GameData updated = null;

        if (Objects.equals(playerColor, "BLACK") && game.blackUsername() == null) {
            updated = game.setBlackUsername(username);
        } else if (Objects.equals(playerColor, "WHITE") && game.whiteUsername() == null) {
            updated = game.setWhiteUsername(username);
        }
        if (!Objects.equals(game.game(), new ChessGame())) {
            updated = game.updateGame(game.game());
        }
        return updated;
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
