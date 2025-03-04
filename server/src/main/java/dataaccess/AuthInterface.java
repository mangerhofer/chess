package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface AuthInterface {
    AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException;
    AuthData getAuthToken(String username) throws DataAccessException;
    String getUserFromAuthToken(AuthData authToken) throws DataAccessException;
    Collection<AuthData> getAllAuthTokens() throws DataAccessException;
    Collection<String> getStringAuthTokens() throws DataAccessException;
    void deleteAuthToken(String authToken) throws DataAccessException;
    void deleteAllAuthTokens() throws DataAccessException;
}
