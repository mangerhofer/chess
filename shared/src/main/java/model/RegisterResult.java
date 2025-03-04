package model;

import com.google.gson.Gson;

public record RegisterResult(int statusCode, String message, String username, AuthData authToken) {
    public RegisterResult registerResult(int statusCode, String message, String username, AuthData authToken) {
        return new RegisterResult(this.statusCode, this.message, this.username, this.authToken);
    }

    public String toString() {
        return new Gson().toJson(this);
    }

}
