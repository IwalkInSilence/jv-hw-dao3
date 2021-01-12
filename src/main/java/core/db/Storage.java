package core.db;

import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    private static long manufacturerId = 0;
    private static long carsId = 0;
    private static long driversId = 0;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturerId++;
        manufacturer.setId(manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void addCar(Car car) {
        carsId++;
        car.setId(carsId);
        cars.add(car);
    }

    public static void addDriver(Driver driver) {
        driversId++;
        driver.setId(driversId);
        drivers.add(driver);
    }
}
