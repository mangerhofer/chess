package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    static final UserService USER_SERVICE = new UserService(new UserDAO(), new AuthDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        USER_SERVICE.deleteAllUsers();
    }

    @Test
    void registerSuccess() throws DataAccessException {
        var user = new UserData("bobusername", "bobpassword", "bob@email.com");
        AuthData result = USER_SERVICE.register(user.username(), user.password(), user.email());

        var users = USER_SERVICE.listUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void registerFail() throws DataAccessException {
        var user = new UserData("bobusername", "bobpassword", "bob@email.com");
        USER_SERVICE.register(user.username(), user.password(), user.email());

        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.register(user.username(), user.password(), user.email());
            throw new DataAccessException(403, "username already taken");
        });

        assertThrows(DataAccessException.class, () -> {
            new UserData("bobusername", "", "bob@email.com");
            throw new DataAccessException(400, "bad request, fields empty");
        });
    }

    @Test
    void loginSuccess() throws DataAccessException {
        USER_SERVICE.register("bob", "bobpassword", "bob@email.com");
        var loginResult = USER_SERVICE.login("bob", "bobpassword");

        assertNotNull(loginResult);
    }

    @Test
    void loginFail() throws DataAccessException {
        USER_SERVICE.register("bob", "bobpassword", "bob@email.com");

        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.login("bob", "pass");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void logoutSuccess() throws DataAccessException {
        AuthData authData = USER_SERVICE.register("bob", "bobpassword", "bob@email.com");
        USER_SERVICE.logout(authData.authToken());

        var tokenList = USER_SERVICE.listAuthTokens();

        assertFalse(tokenList.contains(authData));
    }

    @Test
    void logoutFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.logout(" ");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void listUsersSuccess() throws DataAccessException {
        USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        USER_SERVICE.register( "sally", "sallypassword", "sally@email.com");
        USER_SERVICE.register("fred", "fredpassword", "fred@email.com");

        var actual = USER_SERVICE.listUsers().size();
        assertEquals(3, actual);
    }

    @Test
    void listUsersFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.listUsers();
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void listAuthTokensSuccess() throws DataAccessException {
        USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        USER_SERVICE.register( "sally", "sallypassword", "sally@email.com");
        USER_SERVICE.register("fred", "fredpassword", "fred@email.com");

        var actual = USER_SERVICE.listAuthTokens().size();
        assertEquals(3, actual);
    }

    @Test
    void listAuthTokensFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.listAuthTokens();
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void listStringAuthSuccess() throws DataAccessException {
        USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        USER_SERVICE.register( "sally", "sallypassword", "sally@email.com");
        USER_SERVICE.register("fred", "fredpassword", "fred@email.com");

        var actual = USER_SERVICE.listStringAuthTokens().size();
        assertEquals(3, actual);
    }

    @Test
    void listStringAuthFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.listStringAuthTokens();
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void getAuthTokenSuccess() throws DataAccessException {
        var user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        var authToken = USER_SERVICE.getAuthToken(user.authToken());

        var listedAuthTokens = USER_SERVICE.listAuthTokens();
        assertTrue(listedAuthTokens.contains(authToken));
    }

    @Test
    void getAuthTokenFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.getAuthToken(" ");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void getUsernameSuccess() throws DataAccessException {
        var user = USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        var username = USER_SERVICE.getUsername(user.authToken());

        assertEquals(username, "joe");
    }

    @Test
    void getUsernameFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            USER_SERVICE.getUsername("");
            throw new DataAccessException(401, "unauthorized");
        });
    }

    @Test
    void deleteAllUsers() throws DataAccessException {
        USER_SERVICE.register("joe", "joepassword", "joe@email.com");
        USER_SERVICE.register( "sally", "sallypassword", "sally@email.com");
        USER_SERVICE.register("fred", "fredpassword", "fred@email.com");

        assertEquals(3, USER_SERVICE.listUsers().size());
        USER_SERVICE.deleteAllUsers();
        assertEquals(0, USER_SERVICE.listUsers().size());

        assertEquals(0, USER_SERVICE.listAuthTokens().size());
    }
}
