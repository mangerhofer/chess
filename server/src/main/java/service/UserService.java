package service;

import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import dataaccess.AuthInterface;
import model.AuthData;
import model.UserData;

public class UserService {
    private final UserInterface userInterface;
    private final AuthInterface authInterface;

    public UserService(UserInterface userInterface, AuthInterface authInterface) {
        this.userInterface = userInterface;
        this.authInterface = authInterface;
    }

    public AuthData register(String username, String password, String email) throws DataAccessException {
        userInterface.createUser(username, password, email);
        UserData user = userInterface.getUser(username);

        return authInterface.createAuthToken(user, username, password);
    }

    public AuthData login(UserData user) throws DataAccessException {
        return authInterface.createAuthToken(user, user.username(), user.password());
    }

    public void logout(AuthData token) throws DataAccessException {
        authInterface.deleteAuthToken(token);
    }
}
