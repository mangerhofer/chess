package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface UserInterface {
    public Collection<UserData> userData = new ArrayList<>();
    UserData addUser(UserData u) throws DataAccessException;
    void updateUser(UserData u) throws DataAccessException;
    Collection<UserData> listUsers() throws DataAccessException;
    UserData getUser(int id) throws DataAccessException;
    void deleteUser(UserData u) throws DataAccessException;
    void deleteAllUsers() throws DataAccessException;
}
