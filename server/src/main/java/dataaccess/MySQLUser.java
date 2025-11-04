package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.*;

public class MySQLUser implements UserInterface {
    public MySQLUser() throws DataAccessException {
        configureDatabase();
    }

    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    public void deleteUser(String username) throws DataAccessException {
        var statement = "DELETE FROM UserData WHERE username=?";
        executeUpdate(statement, username);
    }

    public void deleteAllUsers() throws DataAccessException {
        var statement = "TRUNCATE UserData";
        executeUpdate(statement);
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var json = rs.getString("json");
        UserData userData = new Gson().fromJson(json, UserData.class);
        return userData.setUsername(username);
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, java.sql.Types.NULL);
                }
                ps.executeUpdate();

                return 0;
            }
        } catch (SQLException ex) {
            throw new DataAccessException(500, "Unable to update database: %s, %s");
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS UserData (
            'username' varchar(256) NOT NULL,
            'password' varchar(256) NOT NULL,
            'email' varchar(256) NOT NULL,
            'json' TEXT DEFAULT NULL,
            PRIMARY KEY ('username'),
            INDEX(password),
            INDEX(email)
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
