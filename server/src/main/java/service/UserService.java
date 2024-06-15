package service;

import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import dataaccess.AuthInterface;
import model.AuthData;
import model.UserData;

import java.util.Collection;

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

    public void logout(String username) throws DataAccessException {
        authInterface.deleteAuthToken(username);
    }

    public UserData getUser(String username) throws DataAccessException {
        return userInterface.getUser(username);
    }

    public Collection<UserData> getAllUsers() throws DataAccessException {
        return userInterface.listUsers();
    }

    public Collection<AuthData> getAllAuthTokens() throws DataAccessException {
        return authInterface.getAllAuthTokens();
    }

    public void deleteUser(UserData user) throws DataAccessException {
        userInterface.deleteUser(user.username());
        authInterface.deleteAuthToken(user.username());
    }

    public void deleteAllUsers() throws DataAccessException {
        userInterface.deleteAllUsers();
        authInterface.deleteAllAuthTokens();
    }
}
