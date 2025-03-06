package model;

import com.google.gson.Gson;

public record JoinGameRequest(int gameID, String playerColor) {
    public JoinGameRequest setPlayerColor(String playerColor) {
        return new JoinGameRequest(this.gameID, playerColor);
    }

    public JoinGameRequest setGameID(int gameID) {
        return new JoinGameRequest(gameID, this.playerColor);
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
