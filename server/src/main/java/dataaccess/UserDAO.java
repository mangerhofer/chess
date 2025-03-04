package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

public class UserDAO implements UserInterface {
    final private LinkedHashMap<String, UserData> users = new LinkedHashMap<>();

    public UserData createUser(String username, String password, String email) {
        UserData u = new UserData(username, password, email);

        if (!users.containsKey(username)) {
            users.put(username, u);
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
