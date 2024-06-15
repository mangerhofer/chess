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

    public GameData updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {
//    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
//                  This is used when players join a game or when a move is made.
        GameData game = games.get(gameID);
        GameData fake = null;

        games.replace(gameID, game, fake);

        if (game.blackUsername() == null) {
            game = game.setBlackUsername(blackUsername);
        }
        if (game.whiteUsername() == null) {
            game = game.setWhiteUsername(whiteUsername);
        }
        if (!Objects.equals(chessGame, game.game())) {
            game = game.updateGame(chessGame);
        }

        games.replace(gameID, fake, game);

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
