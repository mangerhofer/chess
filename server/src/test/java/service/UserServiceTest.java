package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static final UserService service = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());

    @BeforeEach
    void clear() throws DataAccessException {
        service.deleteAllUsers();
    }

    @Test
    void register() throws DataAccessException {
        var user = new UserData("joe", "joepassword", "joe@email.com");
        service.register(user.username(), user.password(), user.email());

        var users = service.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void login() throws DataAccessException {
        List<AuthData> expected = new ArrayList<>();
        var user = new UserData("joe", "joepassword", "joe@email.com");

        expected.add(service.login(user));

        var actual = service.getAllAuthTokens();
        assertIterableEquals(expected, actual);
    }

    @Test
    void logout() throws DataAccessException {
        List<AuthData> expected = new ArrayList<>();
        var user = new UserData("joe", "joepassword", "joe@email.com");
        service.register("joe", "joepassword", "joe@email.com");
        expected.add(service.register("sally", "sallypassword", "sally@email.com"));
        expected.add(service.register("fred", "fredpassword", "fred@email.com"));

        service.logout(user.username());
        var actual = service.getAllAuthTokens();
        assertIterableEquals(expected, actual);
    }

    @Test
    void getAllUsers() throws DataAccessException {
        List<UserData> expected = new ArrayList<>();
        service.register("joe", "joepassword", "joe@email.com");
        service.register("sally", "sallypassword", "sally@email.com");
        service.register("fred", "fredpassword", "fred@email.com");
        expected.add(new UserData("joe", "joepassword", "joe@email.com"));
        expected.add(new UserData("sally", "sallypassword", "sally@email.com"));
        expected.add(new UserData("fred", "fredpassword", "fred@email.com"));

        var actual = service.getAllUsers();
        assertIterableEquals(expected, actual);
    }

    @Test
    void getAllAuthTokens() throws DataAccessException {
        List<AuthData> expected = new ArrayList<>();
        expected.add(service.register("joe", "joepassword", "joe@email.com"));
        expected.add(service.register("sally", "sallypassword", "sally@email.com"));
        expected.add(service.register("fred", "fredpassword", "fred@email.com"));

        var actual = service.getAllAuthTokens();
        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteUser() throws DataAccessException {
        List<UserData> expected = new ArrayList<>();
        var user1 = new UserData("joe", "joepassword", "joe@email.com");
        var user2 = new UserData("sally", "sallypassword", "sally@email.com");
        var user3 = new UserData("fred", "fredpassword", "fred@email.com");
        service.register("joe", "joepassword", "joe@email.com");
        service.register("sally", "sallypassword", "sally@email.com");
        service.register("fred", "fredpassword", "fred@email.com");

        expected.add(user2);
        expected.add(user3);

        service.deleteUser(user1);
        var actual = service.getAllUsers();
        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteAllUsers() throws DataAccessException {
        service.register("joe", "joepassword", "joe@email.com");
        service.register("sally", "sallypassword", "sally@email.com");
        service.register("fred", "fredpassword", "fred@email.com");

        service.deleteAllUsers();
        assertEquals(0, service.getAllUsers().size());
    }
}
