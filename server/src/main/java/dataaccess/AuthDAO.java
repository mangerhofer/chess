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

        for (Map.Entry<AuthData, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getValue().equals(username)) {
                tokenValue = possToken.getKey();
            } else {
                throw new DataAccessException(401, "Error: unauthorized");
            }
        }

        return tokenValue;
    }

    public String getUserFromAuthToken(AuthData authToken) throws DataAccessException {
        String username = null;

        for (Map.Entry<AuthData, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getKey().equals(authToken)) {
                username = possToken.getValue();
            } else {
                throw new DataAccessException(401, "Error: unauthorized");
            }
        }

        return username;
    }

    public Collection<AuthData> getAllAuthTokens() {
        return tokens.values();
    }

    public void deleteAuthToken(AuthData authToken) {
        tokens.remove(authToken.toString());
        authTokenMap.remove(authToken);
    }

    public void deleteAllAuthTokens() throws DataAccessException {
        tokens.clear();
        authTokenMap.clear();
    }
}
