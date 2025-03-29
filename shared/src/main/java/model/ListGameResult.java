package model;

import com.google.gson.Gson;

public record ListGameResult(int gameID, String whiteUsername, String blackUsername, String gameName) {

    public ListGameResult setWhiteUsername(String username) {
        return new ListGameResult(this.gameID, username, this.blackUsername, this.gameName);
    }

    public ListGameResult setBlackUsername(String username) {
        return new ListGameResult(this.gameID, this.whiteUsername, username, this.gameName);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
