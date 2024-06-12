package dataaccess;

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

        if (!games.containsValue(game)) {
            nextId++;
            games.put(nextId, game);

            return game;
        } else {
            throw new DataAccessException("Game already exists");
        }
    }

    public void updateGame(GameData game) throws DataAccessException{
        if (games.containsValue(game)) {

        } else {
            throw new DataAccessException("Game does not exist");
        }

    }

    public Collection<GameData> listGames() throws DataAccessException{
        if (!games.isEmpty()) {
            return games.values();
        } else {
            throw new DataAccessException("No games found");
        }
    }

    public GameData getGame(GameData game) throws DataAccessException{
        int gameValue = 0;

        for (Map.Entry<Integer, GameData> possGame : games.entrySet()) {
            if (possGame.getValue().equals(game)) {
                gameValue = possGame.getKey();
            }
        }

        if (games.containsKey(gameValue)){
            return games.get(gameValue);
        } else {
            throw new DataAccessException("No such game");
        }
    }

    public void deleteGame(GameData game) throws DataAccessException{
        int gameValue = 0;
        for (Map.Entry<Integer, GameData> possGame : games.entrySet()) {
            if (possGame.getValue().equals(game)) {
                gameValue = possGame.getKey();
            }
        }

        if (games.containsKey(gameValue)){
            games.remove(gameValue);
            userGames.remove(game);
        } else {
            throw new DataAccessException("No such game");
        }
    }

    public void deleteAllGames() throws DataAccessException{
        if (!games.isEmpty()) {
            userGames.clear();
            games.clear();
        } else {
            throw new DataAccessException("No games found");
        }
    }

//    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
//                  This is used when players join a game or when a move is made.
}
