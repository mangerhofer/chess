package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class MySQLAuth implements AuthInterface {

    public MySQLAuth() throws DataAccessException {
        configureDatabase();
    }

    public AuthData createAuthToken(UserData user, String username, String password) throws DataAccessException {
        var statement = "INSERT INTO authData (authToken, username) VALUES (?, ?, ?)";
    }

    public AuthData getAuthToken(String username) throws DataAccessException {
       return null;
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
        var statement = "DELETE FROM authData WHERE authToken=?";
        executeUpdate(statement, authToken);

    }

    public void deleteAllAuthTokens() throws DataAccessException {
        var statement = "DELETE FROM authData WHERE authToken=?";
        executeUpdate(statement);

    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var json = rs.getString("json");
        AuthData authData = new Gson().fromJson(json, AuthData.class);
        return authData.setAuthToken(authToken);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, Types.NULL);
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(500, e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authData (
            'authToken' varchar(256) NOT NULL,
            'username' varchar(256) NOT NULL,
            'json' TEXT DEFAULT NULL,
            PRIMARY KEY ('authToken'),
            INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
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
