package dataaccess;

import model.AuthData;

import java.util.*;

public class AuthDAO implements AuthInterface {
    Collection<AuthData> userTokens;
    final private HashMap<String, AuthData> tokens = new HashMap<>();

    public AuthData createAuthToken(AuthData token) throws DataAccessException {
        token = new AuthData(token.authToken(), token.username());

        String authToken = UUID.randomUUID().toString();
        if (!tokens.containsKey(authToken)) {
            tokens.put(authToken, token);
            return token;
        } else {
            throw new DataAccessException("Auth token already exists");
        }
    }

    public AuthData getAuthToken(AuthData token) throws DataAccessException {
        String tokenValue = null;
        for (Map.Entry<String, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getValue().equals(token)) {
                tokenValue = possToken.getKey();
            }
        }

        if (tokens.containsKey(tokenValue)) {
            return tokens.get(tokenValue);
        } else {
            throw new DataAccessException("Auth token not found");
        }
    }

    public void deleteAuthToken(AuthData token) throws DataAccessException {
        String tokenValue = null;
        for (Map.Entry<String, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getValue().equals(token)) {
                tokenValue = possToken.getKey();
            }
        }

        if (tokens.containsKey(tokenValue)) {

            tokens.remove(tokenValue);
            userTokens.remove(token);
        } else {
            throw new DataAccessException("Auth token not found");
        }

    }

    public void deleteAllAuthTokens() throws DataAccessException {
        if (!tokens.isEmpty() && !userTokens.isEmpty()) {
            userTokens.clear();
            tokens.clear();
        } else {
            throw new DataAccessException("No auth tokens found");
        }
    }

}
