package model;

import java.util.UUID;

public record AuthData(String authToken, String username) {
    public AuthData setAuthToken() {
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, this.username);
    }
}
