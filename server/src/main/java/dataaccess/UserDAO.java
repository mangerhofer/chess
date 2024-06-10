package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class UserDAO implements UserInterface {
    Collection<UserData> userData;
    private int nextId = 1;
    final private HashMap<Integer, UserData> users = new HashMap<>();

    public UserData addUser(UserData u) throws DataAccessException{
        u = new UserData(u.username(), u.password(), u.email());

        nextId++;
        users.put(nextId, u);

        return u;
    }

    public void updateUser(UserData u) throws DataAccessException{

    }

    public Collection<UserData> listUsers() throws DataAccessException{
        return users.values();
    }

    public UserData getUser(int id) throws DataAccessException{
        return users.get(id);
    }

    public void deleteUser(UserData u) throws DataAccessException{
        userData.remove(u);
        users.remove(nextId);
    }

    public void deleteAllUsers() throws DataAccessException{
        userData.clear();
        users.clear();
    }

}
