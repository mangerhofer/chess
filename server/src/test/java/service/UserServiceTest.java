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
    static final UserService service = new UserService(new UserDAO(), new AuthDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        service.deleteAllUsers();
    }

    @Test
    void registerSuccess() throws DataAccessException {
        var user = new UserData("bobusername", "bobpassword", "bob@email.com");
        AuthData result = service.register(user.username(), user.password(), user.email());

        var users = service.listUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void registerFail() throws DataAccessException {
        var user = new UserData("bobusername", "bobpassword", "bob@email.com");
        service.register(user.username(), user.password(), user.email());

        assertThrows(DataAccessException.class, () -> {
            service.register(user.username(), user.password(), user.email());
            throw new DataAccessException(403, "Error: username already taken");
        });

        assertThrows(DataAccessException.class, () -> {
            new UserData("joeusername", "", "joe@email.com");
            throw new DataAccessException(400, "Error: bad request, fields empty");
        });
    }

    @Test
    void loginSuccess() throws DataAccessException {
        var registerResult = service.register("joe", "joepassword", "joe@email.com");
        var loginResult = service.login("joe", "joepassword");

        assertNotNull(loginResult);
    }

    @Test
    void loginFail() throws DataAccessException {
        service.register("joe", "joepassword", "joe@email.com");

        assertThrows(DataAccessException.class, () -> {
            service.login("joe", "pass");
            throw new DataAccessException(401, "Error: unauthorized");
        });

    }

    @Test
    void logoutSuccess() throws DataAccessException {
        service.register("joe", "joepassword", "joe@email.com");
        AuthData authData = service.login("joe", "joepassword");
        service.logout(authData);

        var tokenList = service.listAuthTokens();
        assertEquals(0, tokenList.size());
    }

    @Test
    void listUsers() throws DataAccessException {
        service.register("joe", "joepassword", "joe@email.com");
        service.register( "sally", "sallypassword", "sally@email.com");
        service.register("fred", "fredpassword", "fred@email.com");

        var actual = service.listUsers().size();
        assertEquals(3, actual);
    }

    @Test
    void deleteAllUsers() throws DataAccessException {
        service.register("joe", "joepassword", "joe@email.com");
        service.register( "sally", "sallypassword", "sally@email.com");
        service.register("fred", "fredpassword", "fred@email.com");

        assertEquals(3, service.listUsers().size());
        service.deleteAllUsers();
        assertEquals(0, service.listUsers().size());
    }
}
