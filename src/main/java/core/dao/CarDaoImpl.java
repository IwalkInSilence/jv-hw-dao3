package core.dao;

import core.db.Storage;
import core.lib.Dao;
import core.model.Car;
import java.util.List;
import java.util.stream.Collectors;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Car get(Long id) {
        return getAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().get();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        int indexOfUpdatedCar = getAll().indexOf(get(car.getId()));
        getAll().set(indexOfUpdatedCar, car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return getAll().removeIf(c -> c.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return getAll().stream()
                .filter(car -> car.getDrivers().stream()
                        .anyMatch(driver -> driver.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
