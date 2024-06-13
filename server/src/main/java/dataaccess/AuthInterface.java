package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface AuthInterface {
    AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException;
    AuthData getAuthToken(String username) throws DataAccessException;
    Collection<AuthData> getAllAuthTokens() throws DataAccessException;
    void deleteAuthToken(String username) throws DataAccessException;
    void deleteAllAuthTokens() throws DataAccessException;
}
