package dataaccess;

import model.AuthData;

public interface AuthInterface {
    AuthData createAuthToken(AuthData game) throws DataAccessException;
    AuthData getAuthToken(AuthData token) throws DataAccessException;
    void deleteAuthToken(AuthData token) throws DataAccessException;
    void deleteAllAuthTokens() throws DataAccessException;
}
