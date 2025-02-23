package dataaccess;

import model.AuthData;
import model.UserData;


import java.util.*;

public class AuthDAO implements AuthInterface {
    final private LinkedHashMap<String, AuthData> tokens = new LinkedHashMap<>();
    final private LinkedHashMap<String, String> authTokenMap = new LinkedHashMap<>();

    public AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException {
        if (Objects.equals(user.password(), password)) {
            String auth = UUID.randomUUID().toString();
            AuthData token = new AuthData(auth, username);
            tokens.put(auth, token);
            authTokenMap.put(auth, username);
            return token;
        } else {
            throw new DataAccessException(500, "Invalid username or password");
        }
    }

    public AuthData getAuthToken(String username) {
        String tokenValue = null;

        for (Map.Entry<String, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getValue().equals(username)) {
                tokenValue = possToken.getKey();
            }
        }

        return tokens.get(tokenValue);
    }

    public String getUserFromAuthToken(String authToken) {
        String username = null;

        for (Map.Entry<String, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getKey().equals(authToken)) {
                username = possToken.getValue();
            }
        }

        return username;
    }

    public Collection<AuthData> getAllAuthTokens() {
        return tokens.values();
    }

    public void deleteAuthToken(String authToken) {
        tokens.remove(authToken);
        authTokenMap.remove(authToken);
    }

    public void deleteAllAuthTokens() throws DataAccessException {
        tokens.clear();
        authTokenMap.clear();
    }
}
