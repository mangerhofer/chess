package model;

import com.google.gson.Gson;

public record AuthData(String authToken, String username) {
    public AuthData setAuthToken(String authToken) {
        return new AuthData(authToken, this.username);
    }
    public String toString() {
        return new Gson().toJson(this);
    }
}
