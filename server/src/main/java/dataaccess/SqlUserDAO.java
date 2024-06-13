//package dataaccess;
//
//import com.google.gson.Gson;
//import model.UserData;
//import exception.ResponseException;
//import spark.Response;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.sql.*;
//
//import static java.sql.Statement.RETURN_GENERATED_KEYS;
//import static java.sql.Types.NULL;
//
//public class SqlUserDAO implements UserInterface {
//    public SqlUserDAO() throws DataAccessException {
//        configureDatabase();
//    }
//
//    public UserData createUser(String username, String password, String email) throws DataAccessException {
//        var statement = "INSERT INTO user (username, password, email, json) VALUES (?, ?, ?, ?)";
//        UserData user = new UserData(username, password, email);
//        var json = new Gson().toJson(user);
//        var id = executeUpdate(statement, user.username(), user.password(), user.email(), json);
//        return new UserData(user.username(), user.password(), user.email());
//    }
//
//    public Collection<UserData> listUsers() throws DataAccessException {
//        var result = new ArrayList<UserData>();
//        try (var conn = DatabaseManager.getConnection()) {
//            var statement = "SELECT id, json FROM pet";
//            try (var ps = conn.prepareStatement(statement)) {
//                try (var rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        result.add(readUser(rs));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new DataAccessException(500, String.format("Unable to configure database: %s", e.getMessage()));
//        }
//        return result;
//    }
//
//    public UserData getUser(String username) throws DataAccessException {
//        try (var conn = DatabaseManager.getConnection()) {
//            var statement = "SELECT username, json FROM user WHERE username=?";
//            try (var ps = conn.prepareStatement(statement)) {
////                ps.setInt(1, username);
//                try (var rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        return readUser(rs);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new DataAccessException(500, String.format("Unable to configure database: %s", e.getMessage()));
//        }
//        return null;
//    }
//
//    public void deleteUser(String username) throws DataAccessException {
//        var statement = "DELETE FROM user WHERE username=?";
//        executeUpdate(statement, username);
//    }
//
//    public void deleteAllUsers() throws DataAccessException {
//        var statement = "TRUNCATE pet";
//        executeUpdate(statement);
//    }
//
//    private UserData readUser(ResultSet user) throws SQLException {
//        var id = user.getInt("username");
//        var json = user.getString("json");
//        var pet = new Gson().fromJson(json, UserData.class);
//        return pet.setUsername(String.valueOf(id));
//    }
//
//    private int executeUpdate(String statement, Object... params) throws DataAccessException {
//        try (var conn = DatabaseManager.getConnection()) {
//            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
//                for (var i = 0; i < params.length; i++) {
//                    var param = params[i];
//                    if (param instanceof String p) ps.setString(i + 1, p);
//                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
//                    else if (param instanceof UserData p) ps.setString(i + 1, p.toString());
//                    else if (param == null) ps.setNull(i + 1, NULL);
//                }
//                ps.executeUpdate();
//
//                var rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    return rs.getInt(1);
//                }
//
//                return 0;
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
//        }
//    }
//
//    private final String[] createStatements = {
//            """
//            CREATE TABLE IF NOT EXISTS  user (
//              `username` string NOT NULL,
//              `password` NOT NULL,
//              `email` NOT NULL,
//              `json` TEXT DEFAULT NULL,
//              PRIMARY KEY (`username`),
//              INDEX(username),
//              INDEX(password),
//              INDEX(email)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
//            """
//    };
//
//
//    private void configureDatabase() throws DataAccessException {
//        DatabaseManager.createDatabase();
//        try (var conn = DatabaseManager.getConnection()) {
//            for (var statement : createStatements) {
//                try (var preparedStatement = conn.prepareStatement(statement)) {
//                    preparedStatement.executeUpdate();
//                }
//            }
//        } catch (SQLException ex) {
//            throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
//        }
//    }
//}
