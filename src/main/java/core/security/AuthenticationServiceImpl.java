package core.security;

import core.exception.AuthenticationException;
import core.lib.Inject;
import core.lib.Service;
import core.model.Driver;
import core.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driverFromDB = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect username or password"));
        if (driverFromDB.getPassword().equals(password)) {
            return driverFromDB;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
