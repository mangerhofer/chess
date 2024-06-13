package dataaccess;

import model.AuthData;

import java.util.*;

public class MemoryAuthDAO implements AuthInterface {
    final private LinkedHashMap<String, AuthData> tokens = new LinkedHashMap<>();
    final private LinkedHashMap<String, String> authTokenMap = new LinkedHashMap<>();

    public AuthData createAuthToken(String username) {
        String auth = UUID.randomUUID().toString();
        AuthData token = new AuthData(auth, username);

        tokens.put(auth, token);
        authTokenMap.put(auth, username);
        return token;
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

    public Collection<AuthData> getAllAuthTokens() {
        return tokens.values();
    }

    public void deleteAuthToken(AuthData token) {
        String tokenValue = null;
        for (Map.Entry<String, AuthData> possToken : tokens.entrySet()) {
            if (possToken.getValue().equals(token)) {
                tokenValue = possToken.getKey();
            }
        }

        tokens.remove(tokenValue);
        authTokenMap.remove(tokenValue);
    }

    public void deleteAllAuthTokens() {
        tokens.clear();
        authTokenMap.clear();
    }

}
