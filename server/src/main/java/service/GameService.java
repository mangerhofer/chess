package service;

import dataaccess.DataAccessException;
import dataaccess.AuthInterface;
import dataaccess.GameInterface;
import dataaccess.UserInterface;
import model.AuthData;
import model.GameData;
import model.ListGameResult;

import javax.xml.crypto.Data;
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
            throw new DataAccessException(400, "Bad request");
        }
        return gameInterface.createGame(gameName);
    }

    public Collection<ListGameResult> listGames() throws DataAccessException {
        return gameInterface.listGameResults();
    }

    public GameData joinGame(int gameID, String playerColor, String authToken, String username) throws DataAccessException {
        UserService userService = new UserService(userInterface, authInterface);
        AuthData authData = userService.getAuthToken(authToken);

        return gameInterface.joinGame(gameID, playerColor, authData, username);
    }

    public void deleteAllGames() throws DataAccessException {
        gameInterface.deleteAllGames();
    }
}
