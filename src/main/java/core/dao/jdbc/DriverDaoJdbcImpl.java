package core.dao.jdbc;

import core.dao.DriverDao;
import core.lib.Dao;
import core.model.Driver;
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
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers (`name`, `license_number`, `login`, `password` )"
                + " VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setString(3, driver.getLogin());
            statement.setString(4, driver.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create Driver "
                    + driver.toString() + " in DB", e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers WHERE id = ? AND deleted = false";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver with Id " + id, e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = false";
        List<Driver> driverList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driverList.add(getDriver(resultSet));
            }
            return driverList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't return List of drivers", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers SET name = ?, license_number = ? "
                + "WHERE (id = ?) AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setLong(3, driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update of driver with id "
                    + driver.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers SET deleted = true WHERE (id = ?) AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver with id "
                    + id, e);
        }
    }

    private static Driver getDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Driver driver = new Driver(name, licenseNumber);
        driver.setLogin(login);
        driver.setPassword(password);
        driver.setId(id);
        return driver;
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        String query = "SELECT * FROM drivers WHERE login = ? AND deleted = false";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find driver with login " + login, e);
        }
        return Optional.ofNullable(driver);
    }
}
