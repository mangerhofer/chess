package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDAOTest {
    private AuthInterface getDataAccess(Class<? extends AuthInterface> databaseClass) throws DataAccessException {
        AuthInterface ad = new AuthDAO();

        ad.deleteAllAuthTokens();
        return ad;
    }

    @ParameterizedTest
    @ValueSource(classes = {AuthDAO.class})
    void createAuthToken(Class<? extends AuthInterface> dbClass) throws DataAccessException {
        AuthInterface dataAccess = getDataAccess(dbClass);
        String auth = UUID.randomUUID().toString();
        UserData user = new UserData("joe", "joepassword", "joe@email.com");

        var authToken = new AuthData(auth, "joe");
        assertDoesNotThrow(() -> dataAccess.createAuthToken(user, "joe", "joepassword"));
    }

    @ParameterizedTest
    @ValueSource(classes = {AuthDAO.class})
    void getUserFromAuthToken(Class<? extends AuthInterface> dbClass) throws DataAccessException {
        AuthInterface dataAccess = getDataAccess(dbClass);
        UserData user = new UserData("joe", "joepassword", "joe@email.com");

        AuthData user1 = dataAccess.createAuthToken(user,"joe", "joepassword");

        String username = dataAccess.getUserFromAuthToken(user1);

        assertEquals("joe", username);
    }

    @ParameterizedTest
    @ValueSource(classes = {AuthDAO.class})
    void getAllAuthTokens(Class<? extends AuthInterface> dbClass) throws DataAccessException {
        AuthInterface dataAccess = getDataAccess(dbClass);
        UserData user = new UserData("joe", "joepassword", "joe@email.com");
        UserData user2 = new UserData("sally", "sallypassword", "sally@email.com");
        UserData user3 = new UserData("fred", "fredpassword", "fred@email.com");

        List<AuthData> expected = new ArrayList<>();
        expected.add(dataAccess.createAuthToken(user,"joe", "joepassword"));
        expected.add(dataAccess.createAuthToken(user2,"sally", "sallypassword"));
        expected.add(dataAccess.createAuthToken(user3,"fred", "fredpassword"));

        var actual = dataAccess.getAllAuthTokens();
        assertAuthTokenCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {AuthDAO.class})
    void deleteAuthToken(Class<? extends AuthInterface> dbClass) throws DataAccessException {
        AuthInterface dataAccess = getDataAccess(dbClass);
        UserData user = new UserData("joe", "joepassword", "joe@email.com");
        UserData user2 = new UserData("sally", "sallypassword", "sally@email.com");
        UserData user3 = new UserData("fred", "fredpassword", "fred@email.com");

        List<AuthData> expected = new ArrayList<>();
        var deleteAuthToken = dataAccess.createAuthToken(user,"joe", "joepassword");
        expected.add(dataAccess.createAuthToken(user2,"sally", "sallypassword"));
        expected.add(dataAccess.createAuthToken(user3,"fred", "fredpassword"));

        dataAccess.deleteAuthToken(deleteAuthToken.authToken());

        var actual = dataAccess.getAllAuthTokens();
        assertAuthTokenCollectionEqual(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(classes = {AuthDAO.class})
    void deleteAllAuthTokens(Class<? extends AuthInterface> dbClass) throws Exception {
        AuthInterface dataAccess = getDataAccess(dbClass);
        UserData user = new UserData("joe", "joepassword", "joe@email.com");
        UserData user2 = new UserData("sally", "sallypassword", "sally@email.com");

        dataAccess.createAuthToken(user,"joe", "joepassword");
        dataAccess.createAuthToken(user2,"sally", "sallypassword");

        dataAccess.deleteAllAuthTokens();

        var actual = dataAccess.getAllAuthTokens();
        assertEquals(0, actual.size());
    }

    public static void assertAuthTokenEqual(AuthData expected, AuthData actual) {
        assertEquals(expected.authToken(), actual.authToken());
        assertEquals(expected.username(), actual.username());
    }

    public static void assertAuthTokenCollectionEqual(Collection<AuthData> expected, Collection<AuthData> actual) {
        AuthData[] actualList = actual.toArray(new AuthData[]{});
        AuthData[] expectedList = expected.toArray(new AuthData[]{});
        assertEquals(expectedList.length, actualList.length);
        for (var i = 0; i < actualList.length; i++) {
            assertAuthTokenEqual(expectedList[i], actualList[i]);
        }
    }
}
