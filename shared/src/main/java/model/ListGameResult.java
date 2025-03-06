package model;

import com.google.gson.Gson;

public record ListGameResult(int gameID, String whiteUsername, String blackUsername, String gameName) {
    public ListGameResult setGameID(int gameID) {
        return new ListGameResult(gameID, this.whiteUsername, this.blackUsername, this.gameName);
    }

    public ListGameResult setWhiteUsername(String username) {
        return new ListGameResult(this.gameID, username, this.blackUsername, this.gameName);
    }

    public ListGameResult setBlackUsername(String username) {
        return new ListGameResult(this.gameID, this.whiteUsername, username, this.gameName);
    }

    public ListGameResult setGameName(String gameName) {
        return new ListGameResult(this.gameID, this.whiteUsername, this.blackUsername, gameName);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
