package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.*;

public class MemoryAuthDAO implements AuthInterface {
    final private LinkedHashMap<String, AuthData> tokens = new LinkedHashMap<>();
    final private LinkedHashMap<String, String> authTokenMap = new LinkedHashMap<>();

    public AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException {
        String auth = null;
        AuthData token = new AuthData(auth, username);

        if (Objects.equals(user.password(), password)) {
            auth = UUID.randomUUID().toString();
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

    public Collection<AuthData> getAllAuthTokens() {
        return tokens.values();
    }

    public void deleteAuthToken(String username) {
        String tokenValue = null;
        for (Map.Entry<String, String> possToken : authTokenMap.entrySet()) {
            if (possToken.getValue().equals(username)) {
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
