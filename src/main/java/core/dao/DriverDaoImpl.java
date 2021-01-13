package core.dao;

import core.db.Storage;
import core.lib.Dao;
import core.model.Driver;
import java.util.List;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Driver get(Long id) {
        return getAll().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        int indexOfDriverToUpdate = Storage.drivers.indexOf(get(driver.getId()));
        getAll().set(indexOfDriverToUpdate, driver);
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return getAll().removeIf(d -> d.getId().equals(id));
    }
}
