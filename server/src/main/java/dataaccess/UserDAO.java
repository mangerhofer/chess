package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

public class UserDAO implements UserInterface {
    final private LinkedHashMap<String, UserData> users = new LinkedHashMap<>();

    public UserData createUser(String username, String password, String email) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserData u = new UserData(username, hashedPassword, email);

        if (!users.containsKey(username)) {
            users.put(username, u);
        } else {
            throw new DataAccessException(403, "Error: Username taken");
        }

        return u;
    }

    public Collection<UserData> listUsers() {
        return users.values();
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

    public void deleteUser(String username) {
        users.remove(username);
    }

    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDAO userDAO = (UserDAO) o;
        return Objects.equals(users, userDAO.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }
}
