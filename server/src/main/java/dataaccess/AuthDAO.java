package dataaccess;

import model.AuthData;
import model.UserData;


import java.util.*;

public class AuthDAO implements AuthInterface {
    final private LinkedHashMap<String, AuthData> tokens = new LinkedHashMap<>();
    final private LinkedHashMap<AuthData, String> authTokenMap = new LinkedHashMap<>();

    public AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException {
        if (Objects.equals(user.password(), password)) {
            String auth = UUID.randomUUID().toString();
            AuthData token = new AuthData(auth, username);
            tokens.put(auth, token);
            authTokenMap.put(token, username);
            return token;
        } else {
            throw new DataAccessException(401, "Invalid username or password");
        }
    }

    public AuthData getAuthToken(String username) throws DataAccessException {
        AuthData tokenValue = null;
        boolean found = false;

        for (Map.Entry<AuthData, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getValue().equals(username)) {
                tokenValue = possToken.getKey();
                found = true;
            }
        }

        if (!found) {
            throw new DataAccessException(401, "unauthorized");
        }

        return tokenValue;
    }

    public String getUserFromAuthToken(AuthData authToken) throws DataAccessException {
        String username = null;
        boolean found = false;

        for (Map.Entry<AuthData, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getKey().equals(authToken)) {
                username = possToken.getValue();
                found = true;
            }
        }

        if (!found) {
            throw new DataAccessException(401, "unauthorized");
        }


        return username;
    }

    public String getUsernameFromAuthToken(String authToken) throws DataAccessException {
        AuthData authData = null;
        for (Map.Entry<String, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getKey().equals(authToken)) {
                authData = possToken.getValue();
            }
        }

        return getUserFromAuthToken(authData);
    }

    public Collection<AuthData> getAllAuthTokens() {
        return tokens.values();
    }

    public Collection<String> getStringAuthTokens() {
        return tokens.keySet();
    }

    public void deleteAuthToken(String authToken) {
        AuthData authData = null;

        for (Map.Entry<String, AuthData> possAuthData : tokens.entrySet()) {
            if (possAuthData.getKey().equals(authToken)) {
                authData = possAuthData.getValue();
            }
        }

        tokens.remove(authToken);
        authTokenMap.remove(authData);
    }

    public void deleteAllAuthTokens() throws DataAccessException {
        tokens.clear();
        authTokenMap.clear();
    }
}
