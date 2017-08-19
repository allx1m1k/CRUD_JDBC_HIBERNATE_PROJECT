package main.project_files.dao.jdbc_DAO;

import main.project_files.connections.ConnectionToDB;
import main.project_files.dao.CompanyDAO;
import main.project_files.models.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCompanyDAO implements CompanyDAO {

    private final static String SAVE = "INSERT INTO companies(title) VALUES(?)";
    private final static String FIND_BY_ID = "SELECT * FROM companies where id = ?";
    private final static String UPDATE = "UPDATE companies SET title = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM companies WHERE id = ?";
    private final static String FIND_ALL = "SELECT * FROM companies";
    private final static String FIND_BY_NAME = "SELECT * FROM companies WHERE title = ?";
    private final static String GET_LAST_INSERT = "SELECT LAST_INSERT_ID()";


    private ConnectionToDB connectionToDB;

    public JDBCCompanyDAO(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }


    @Override
    public Long save(Company obj) {
        Long id;
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
             Statement statement = connection.createStatement()) {
            preparedStatement.setString(1, obj.getTitle());
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(GET_LAST_INSERT);
            resultSet.next();
            id = resultSet.getLong(1);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company findByID(Long aLong) {
        Company company = new Company(aLong, "");
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = new Company(resultSet.getLong("id"), resultSet.getString("title"));
            }
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Company company) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, company.getTitle());
            preparedStatement.setLong(2, company.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Company obj) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        List<Company> allCompanies = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allCompanies.add(
                        new Company(
                                resultSet.getLong("id"),
                                resultSet.getString("title")
                        )
                );
            }
            return allCompanies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Company findByName(String companyName) {
        Company company = new Company(0, companyName);
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = new Company(
                        resultSet.getLong("id"),
                        resultSet.getString("title")
                );
            }
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}