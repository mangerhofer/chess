package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryGameDAO;
import org.junit.jupiter.api.Test;

class ClearServiceTest {
    static final ClearService service = new ClearService(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());

    @Test
    void clear() throws DataAccessException {
        service.clear();
    }
}
