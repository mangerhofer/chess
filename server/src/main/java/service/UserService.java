package service;

import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import dataaccess.AuthInterface;
import model.AuthData;
import model.RegisterResult;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.Collection;

public class UserService {
    private final UserInterface userInterface;
    private final AuthInterface authInterface;

    public UserService(UserInterface userInterface, AuthInterface authInterface) {
        this.userInterface = userInterface;
        this.authInterface = authInterface;
    }

    public AuthData register(String username, String password, String email) throws DataAccessException {
        var users = userInterface.listUsers();
        UserData user = userInterface.createUser(username, password, email);
        return authInterface.createAuthToken(user, user.username(), user.password());
    }

    public Collection<UserData> listUsers() throws DataAccessException {
        return userInterface.listUsers();
    }

    public AuthData login(String username, String password) throws DataAccessException {
        UserData user = userInterface.getUser(username);

        if (user.password().equals(password)) {
            return authInterface.createAuthToken(user, username, password);
        } else {
            throw new DataAccessException(401, "Error: Invalid password");
        }
//        return authInterface.createAuthToken(user, username, password);
    }

    public void logout(AuthData authToken) throws DataAccessException {
        authInterface.deleteAuthToken(authToken);
    }

    public void deleteUser(String username) throws DataAccessException {
        userInterface.deleteUser(username);
    }

    public void deleteAllUsers() throws DataAccessException {
        userInterface.deleteAllUsers();
    }
}
