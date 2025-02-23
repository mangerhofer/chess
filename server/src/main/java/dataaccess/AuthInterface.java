package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public interface AuthInterface {
    public Collection<AuthData> userTokens = new ArrayList<>();
    AuthData createAuthToken(AuthData game) throws DataAccessException;
    Collection<AuthData> listGames() throws DataAccessException;
    AuthData getAuthToken(AuthData token) throws DataAccessException;
    void deleteAuthToken(AuthData token) throws DataAccessException;
    void deleteAllAuthTokens() throws DataAccessException;
}
