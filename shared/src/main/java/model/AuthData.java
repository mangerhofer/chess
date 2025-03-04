package model;

import com.google.gson.Gson;

import java.util.UUID;

public record AuthData(String authToken, String username) {
    public AuthData setAuthToken() {
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, this.username);
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
