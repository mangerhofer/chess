package service;

import dataaccess.AuthInterface;
import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import dataaccess.GameInterface;

public class ClearService {
    private final AuthInterface auth;
    private final GameInterface game;
    private final UserInterface user;

    public ClearService(AuthInterface auth, GameInterface game, UserInterface user) {
        this.auth = auth;
        this.game = game;
        this.user = user;
    }

    public void clear() throws DataAccessException {
        auth.deleteAllAuthTokens();
        game.deleteAllGames();
        user.deleteAllUsers();
    }
}
