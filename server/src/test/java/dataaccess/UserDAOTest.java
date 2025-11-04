package dataaccess;

import model.UserData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserInterface getDataAccess(Class<? extends UserInterface> databaseClass) throws DataAccessException {
        UserInterface ad;

        if (databaseClass.equals(MySQLUser.class)) {
            ad = new MySQLUser();
        } else {
            ad = new UserDAO();
        }

        ad.deleteAllUsers();
        return ad;
    }

    @ParameterizedTest
    @ValueSource(classes = {UserDAO.class})
    void createUser(Class<? extends UserDAO> dbClass) throws DataAccessException {
        UserInterface dataAccess = getDataAccess(dbClass);

        var user = new UserData("joe", "joepassword", "joe@email.com");
        assertDoesNotThrow(() -> dataAccess.createUser(user.username(), user.password(), user.email()));
    }

    @ParameterizedTest
    @ValueSource(classes = {UserDAO.class})
    void listUsers(Class<? extends UserInterface> dbClass) throws DataAccessException {
        UserInterface dataAccess = getDataAccess(dbClass);

        Collection<UserData> expected = new ArrayList<>();
        expected.add(dataAccess.createUser("joe", "joepassword", "joe@email.com"));
        expected.add(dataAccess.createUser( "sally", "sallypassword", "sally@email.com"));
        expected.add(dataAccess.createUser("fred", "fredpassword", "fred@email.com"));

        var actual = dataAccess.listUsers();
        assertUserCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {UserDAO.class})
    void deleteUser(Class<? extends UserInterface> dbClass) throws DataAccessException {
        UserInterface dataAccess = getDataAccess(dbClass);

        List<UserData> expected = new ArrayList<>();
        var deletePet = dataAccess.createUser("joe", "joepassword", "joe@email.com");
        expected.add(dataAccess.createUser("sally", "sallypassword", "sally@email.com"));
        expected.add(dataAccess.createUser("fred", "fredpassword", "fred@email.com"));

        dataAccess.deleteUser(deletePet.username());

        var actual = dataAccess.listUsers();
        assertUserCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {UserDAO.class})
    void deleteAllUsers(Class<? extends UserInterface> dbClass) throws Exception {
        UserInterface dataAccess = getDataAccess(dbClass);

        dataAccess.createUser("joe,", "joepassword", "joe@email.com");
        dataAccess.createUser("sally,", "sallypassword", "sally@email.com");

        dataAccess.deleteAllUsers();

        var actual = dataAccess.listUsers();
        assertEquals(0, actual.size());
    }

    public static void assertUserEqual(UserData expected, UserData actual) {
        assertEquals(expected.username(), actual.username());
        assertEquals(expected.password(), actual.password());
        assertEquals(expected.email(), actual.email());
    }

    public static void assertUserCollectionEqual(Collection<UserData> expected, Collection<UserData> actual) {
        UserData[] actualList = actual.toArray(new UserData[]{});
        UserData[] expectedList = expected.toArray(new UserData[]{});
        assertEquals(expectedList.length, actualList.length);
        for (var i = 0; i < actualList.length; i++) {
            assertUserEqual(expectedList[i], actualList[i]);
        }
    }
}
