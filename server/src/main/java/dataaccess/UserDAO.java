package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDAO implements UserInterface {
    Collection<UserData> userData;
    private int nextId = 1;
    final private HashMap<Integer, UserData> users = new HashMap<>();

    public UserData addUser(UserData u) throws DataAccessException{
        if (!userData.contains(u)) {
            u = new UserData(u.username(), u.password(), u.email());

            nextId++;
            users.put(nextId, u);
            return u;
        } else {
            throw new DataAccessException("User already exists");
        }
    }

    public void updateUser(UserData u) throws DataAccessException{

    }

    public Collection<UserData> listUsers() throws DataAccessException{
        if (!users.isEmpty()) {
            return users.values();
        } else {
            throw new DataAccessException("No users found");
        }
    }

    public UserData getUser(int id) throws DataAccessException{
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new DataAccessException("User not found");
        }
    }

    public void deleteUser(UserData u) throws DataAccessException{
        int userValue = 0;
        for (Map.Entry<Integer, UserData> possGame : users.entrySet()) {
            if (possGame.getValue().equals(u)) {
                userValue = possGame.getKey();
            }
        }

        if (users.containsKey(userValue) && userData.contains(u)) {
            users.remove(userValue);
            userData.remove(u);
        } else {
            throw new DataAccessException("User not found");
        }
    }

    public void deleteAllUsers() throws DataAccessException{
        if (!users.isEmpty() && !userData.isEmpty()) {
            userData.clear();
            users.clear();
        } else {
            throw new DataAccessException("No users found");
        }
    }

}
