package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameDAO implements GameInterface {
    Collection<GameData> userGames;
    private int nextId = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(GameData game) throws DataAccessException{
        game = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());

        nextId++;
        games.put(nextId, game);

        return game;
    }

    public void updateGame(GameData game) throws DataAccessException{

    }

    public Collection<GameData> listGames() throws DataAccessException{
        return games.values();
    }

    public GameData getGame(int gameID) throws DataAccessException{
        return games.get(gameID);
    }

    public void deleteGame(GameData game) throws DataAccessException{
        int gameValue = 0;
        for (Map.Entry<Integer, GameData> possGame : games.entrySet()) {
            if (possGame.getValue().equals(game)) {
                gameValue = possGame.getKey();
            }
        }

        games.remove(gameValue);
        userGames.remove(game);
    }

    public void deleteAllGames() throws DataAccessException{
        userGames.clear();
        games.clear();
    }

//    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
//                  This is used when players join a game or when a move is made.
}
