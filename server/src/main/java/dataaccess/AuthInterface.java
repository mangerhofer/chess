package dataaccess;

import model.AuthData;

import java.util.Collection;

public interface AuthInterface {
    AuthData createAuthToken(String username) throws DataAccessException;
    AuthData getAuthToken(String username) throws DataAccessException;
    Collection<AuthData> getAllAuthTokens() throws DataAccessException;
    void deleteAuthToken(AuthData token) throws DataAccessException;
    void deleteAllAuthTokens() throws DataAccessException;
}
