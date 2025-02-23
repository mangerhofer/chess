package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.LinkedHashMap;

public class UserDAO implements UserInterface {
    final private LinkedHashMap<String, UserData> users = new LinkedHashMap<>();

    public UserData createUser(String username, String password, String email) {
        UserData u = new UserData(username, password, email);

        users.put(username, u);
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
}
