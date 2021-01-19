package core.dao.jdbc;

import core.dao.CarDao;
import core.lib.Dao;
import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import core.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (manufacturer_id, model) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car "
                    + car + " in DB", e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT c.id, manufacturer_id, model, name, country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON m.id = c.manufacturer_id "
                + "WHERE c.id = ? AND c.deleted = FALSE;";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
                return Optional.ofNullable(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car with ID " + id, e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.id, manufacturer_id, model, name, country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = false AND m.deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Car> carList = new ArrayList<>();
            while (resultSet.next()) {
                carList.add(getCar(resultSet));
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Cars from DB ", e);
        }
    }

    @Override
    public Car update(Car car) {
        String deleteQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        String insertQuery = "INSERT INTO cars_drivers (driver_id, car_id) VALUES (?, ?)";
        String query = "UPDATE cars SET manufacturer_id = ?, model = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
            deleteStatement.setLong(1, car.getId());
            deleteStatement.executeUpdate();
            insertStatement.setLong(2, car.getId());
            List<Driver> drivers = car.getDrivers();
            for (Driver driver : drivers) {
                insertStatement.setLong(1, driver.getId());
                insertStatement.executeUpdate();
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car with id "
                    + car.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of car with id "
                    + id + " is failed", e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT * "
                + "FROM cars_drivers cd "
                + "INNER JOIN cars c ON cd.car_id = c.id "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "INNER JOIN drivers d ON d.id = cd.driver_id "
                + "WHERE cd.driver_id = ? AND d.deleted = FALSE AND c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Error with getting list of Cars from Driver "
                    + driverId, e);
        }
    }

    private static List<Driver> getDrivers(Long carId) {
        String query = "SELECT * "
                + "FROM cars_drivers cd "
                + "INNER JOIN drivers d "
                + "ON d.id = cd.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("We can't get drivers from car with id " + carId, e);
        }
    }

    private static Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String model = resultSet.getString("model");
        Manufacturer manufacturer = getManufacturer(resultSet);
        Car car = new Car(model, manufacturer);
        car.setId(id);
        car.setDrivers(getDrivers(id));
        return car;
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }

    private static Driver getDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        core.model.Driver driver = new Driver(name, licenseNumber);
        driver.setId(id);
        return driver;
    }
}

