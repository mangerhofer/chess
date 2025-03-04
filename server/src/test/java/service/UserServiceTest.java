package service;

import dataaccess.AuthDAO;
import dataaccess.AuthInterface;
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
    void register() throws DataAccessException {
        var user = new UserData("bobusername", "bobpassword", "bob@email.com");
        AuthData result = service.register(user.username(), user.password(), user.email());
//
//        var expected = {"username:"}
//
//        assertEquals();

        var users = service.listUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void loginSuccess() throws DataAccessException {
        var registerResult = service.register("joe", "joepassword", "joe@email.com");
        var loginResult = service.login("joe", "joepassword");

        assertNotNull(loginResult);
//        assertTrue(loginResult.getClass().getName(), AuthData.class);

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
