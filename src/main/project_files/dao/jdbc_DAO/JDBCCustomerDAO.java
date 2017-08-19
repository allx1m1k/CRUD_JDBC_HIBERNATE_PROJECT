package main.project_files.dao.jdbc_DAO;

import main.project_files.Constants.Constants;
import main.project_files.connections.ConnectionToDB;
import main.project_files.dao.CustomerDAO;
import main.project_files.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCustomerDAO implements CustomerDAO {


    private final static String SAVE = "INSERT INTO customers(title) VALUES(?)";
    private final static String FIND_BY_ID = "SELECT * FROM customers where id = ?";
    private final static String UPDATE = "UPDATE customers SET title = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM customers WHERE id = ?";
    private final static String FIND_BY_NAME = "SELECT * FROM customers WHERE title = ?";
    private final static String FIND_ALL = "SELECT * FROM customers";

    private ConnectionToDB connectionToDB;
    private Connection connection;

    public JDBCCustomerDAO(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public JDBCCustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(Customer obj) {
        Long id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
             PreparedStatement preparedStatement1 = connection.prepareStatement(Constants.GET_LAST_ID)) {
            preparedStatement.setString(1, obj.getTitle());
            preparedStatement1.execute();
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            id = resultSet.getLong(1);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Customer findByID(Long id) {
        Customer customer = new Customer(id, "");
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customers = buildCustomersFromResultSet(resultSet);
            if (customers.size() > 0) {
                customer = customers.get(0);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public void update(Customer obj) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, obj.getTitle());
            preparedStatement.setLong(2, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Customer obj) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, obj.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            customers = buildCustomersFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public Customer findByName(String title) {
        Customer customer = new Customer(0, title);
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customers = buildCustomersFromResultSet(resultSet);
            if (customers.size() > 0) {
                customer = customers.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }


    private static List<Customer> buildCustomersFromResultSet(ResultSet rs) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        while (rs.next()) {
            customer = new Customer(rs.getLong("id"), rs.getString("title"));
            customers.add(customer);
        }
        return customers;
    }
}