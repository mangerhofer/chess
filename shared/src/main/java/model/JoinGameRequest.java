package model;

import com.google.gson.Gson;

public record JoinGameRequest(int gameID, String playerColor) {
    public String toString() {
        return new Gson().toJson(this);
    }
}
