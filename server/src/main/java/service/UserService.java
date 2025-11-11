package service;

import dataaccess.DataAccessException;
import dataaccess.UserInterface;
import dataaccess.AuthInterface;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;

public class UserService {
    private final UserInterface userInterface;
    private final AuthInterface authInterface;

    public UserService(UserInterface userInterface, AuthInterface authInterface) {
        this.userInterface = userInterface;
        this.authInterface = authInterface;
    }

    public AuthData register(String username, String password, String email) throws DataAccessException {
        UserData user = userInterface.createUser(username, password, email);
        return authInterface.createAuthToken(user, user.username(), user.password());
    }

    public AuthData login(String username, String password) throws DataAccessException {
        if (username == null || password == null) {
            throw new DataAccessException(400, "Error: unauthorized");
        } else {
            UserData user = userInterface.getUser(username);

            if (user == null) {
                throw new DataAccessException(401, "Error: unauthorized");
            }

            var hashedPassword = user.password();
            boolean validPassword = BCrypt.checkpw(password, hashedPassword);

            var users = listUsers();

            if (!users.contains(user)) {
                throw new DataAccessException(401, "Error: unauthorized");
            } else if (!validPassword) {
                throw new DataAccessException(401, "Error: unauthorized");
            } else {
                return authInterface.createAuthToken(user, username, hashedPassword);
            }
        }
    }

    public void logout(String authToken) throws DataAccessException {
        var tokenList = authInterface.getStringAuthTokens();
        if (!tokenList.contains(authToken)) {
            throw new DataAccessException(401, "Error: unauthorized");
        } else {
            authInterface.deleteAuthToken(authToken);
        }
    }

    public Collection<UserData> listUsers() throws DataAccessException {
        return userInterface.listUsers();
    }

    public Collection<AuthData> listAuthTokens() throws DataAccessException {
        return authInterface.getAllAuthTokens();
    }

    public Collection<String> listStringAuthTokens() throws DataAccessException {
        return authInterface.getStringAuthTokens();
    }

    public AuthData getAuthToken(String authToken) throws DataAccessException {
        String username = authInterface.getUsernameFromAuthToken(authToken);
        return authInterface.getAuthToken(username);
    }

    public String getUsername(String authToken) throws DataAccessException {
        return authInterface.getUsernameFromAuthToken(authToken);
    }

    public void deleteAllUsers() throws DataAccessException {
        userInterface.deleteAllUsers();
        authInterface.deleteAllAuthTokens();
    }
}
