package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MySQLAuth implements AuthInterface {

    public MySQLAuth() throws DataAccessException {
        configureDatabase();
    }

    public AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException {
        var statement = "INSERT INTO authData () VALUES (?, ?, ?)";
        String json = new Gson().toJson();
        int id = executeUpdate(statement, user, username, password, json);
        return new AuthData(authToken, username);
    }

    public AuthData getAuthToken(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, json FROM authData WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return
                    }
                }
            }
        }
    }

    public String getUsernameFromAuthToken(String authToken) throws DataAccessException {
        return "";
    }

    public String getUserFromAuthToken(AuthData authToken) throws DataAccessException {
        return "";
    }

    public Collection<AuthData> getAllAuthTokens() throws DataAccessException {
        return List.of();
    }

    public Collection<String> getStringAuthTokens() throws DataAccessException {
        return List.of();
    }

    public void deleteAuthToken(String authToken) throws DataAccessException {

    }

    public void deleteAllAuthTokens() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authData (
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(500, "Unable to configure database: %s");
        }
    }
}
