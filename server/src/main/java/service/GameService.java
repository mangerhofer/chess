package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.ListGameResult;

import java.util.Collection;

public class GameService {
    private final UserInterface userInterface;
    private final AuthInterface authInterface;
    private final GameInterface gameInterface;

    public GameService(GameInterface gameInterface, AuthInterface authInterface, UserInterface userInterface) {
        this.gameInterface = gameInterface;
        this.authInterface = authInterface;
        this.userInterface = userInterface;
    }

    public GameData createGame(String authToken, String gameName) throws DataAccessException {
        if (gameName == null) {
            throw new DataAccessException(400, "Error: Bad request");
        }
        return gameInterface.createGame(gameName);
    }

    public Collection<ListGameResult> listGames() throws DataAccessException {
        return gameInterface.listGameResults();
    }

    public GameData joinGame(int gameID, String playerColor, AuthData authData, String username) throws DataAccessException {
        UserService userService = new UserService(userInterface, authInterface);

        boolean found = false;
        var findGame = gameInterface.listGames();
        for (GameData possGames : findGame) {
            if (possGames == null) {
                break;
            }
            if (gameID == possGames.gameID()) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new DataAccessException(400, "Error: bad request");
        }


        return gameInterface.joinGame(gameID, playerColor, authData, username);
    }

    public void deleteAllGames() throws DataAccessException {
        gameInterface.deleteAllGames();
    }
}
