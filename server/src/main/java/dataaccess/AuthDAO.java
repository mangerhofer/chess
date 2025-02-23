package dataaccess;

import model.AuthData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthDAO {
    Collection<AuthData> userTokens;
    private int nextId = 1;
    final private HashMap<Integer, AuthData> tokens = new HashMap<>();

    public AuthData createAuthToken(AuthData token) throws DataAccessException {
        token = new AuthData(token.authToken(), token.username());
        nextId++;
        tokens.put(nextId, token);

        return token;
    }

    public Collection<AuthData> listGames() throws DataAccessException {
        return List.of();
    }

    public AuthData getAuthToken(AuthData token) throws DataAccessException {
        int tokenValue = 0;
        for (Map.Entry<Integer, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getValue().equals(token)) {
                tokenValue = possToken.getKey();
            }
        }

        return tokens.get(tokenValue);
    }

    public void deleteAuthToken(AuthData token) throws DataAccessException {
        int tokenValue = 0;
        for (Map.Entry<Integer, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getValue().equals(token)) {
                tokenValue = possToken.getKey();
            }
        }

        tokens.remove(tokenValue);
        userTokens.remove(token);
    }

    public void deleteAllAuthTokens() throws DataAccessException {
        userTokens.clear();
        tokens.clear();
    }
}
