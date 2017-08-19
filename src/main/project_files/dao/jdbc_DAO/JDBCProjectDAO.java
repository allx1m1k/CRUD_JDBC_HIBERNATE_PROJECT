package main.project_files.dao.jdbc_DAO;

import main.project_files.Constants.Constants;
import main.project_files.dao.CompanyDAO;
import main.project_files.dao.CustomerDAO;
import main.project_files.dao.ProjectDAO;
import main.project_files.models.Company;
import main.project_files.models.Customer;
import main.project_files.models.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCProjectDAO implements ProjectDAO {

    private static final String SAVE = "INSERT INTO projects (id,title,cost,company_id,customer_id) VALUES(?,?,?,?,?)";
    private static final String FIND_BY_ID = "SELECT * FROM projects WHERE id = ?";
    private static final String UPDATE = "UPDATE projects SET title = ?, cost = ?, company_id = ?, customer_id = ?, WHERE id = ?";
    private static final String DELETE = "DELETE FROM projects WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM projects";
    private static final String FIND_BY_TITLE = "SELECT * FROM projects WHERE TITLE LIKE ?";


    private CompanyDAO companyDAO;
    private CustomerDAO customerDAO;
    private Connection connection;

    public JDBCProjectDAO(CompanyDAO companyDAO, CustomerDAO customerDAO, Connection connection) {
        this.companyDAO = companyDAO;
        this.customerDAO = customerDAO;
        this.connection = connection;
    }

    public JDBCProjectDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(Project project) {
        Long id = null;
        try (PreparedStatement preparedStatementSAVE = connection.prepareStatement(SAVE);
             PreparedStatement preparedStatementLastID = connection.prepareStatement(Constants.GET_LAST_ID)) {
            preparedStatementSAVE.setString(1, project.getTitle());
            preparedStatementSAVE.setInt(2, project.getCost());
            preparedStatementSAVE.setLong(3, project.getCompany().getId());
            preparedStatementSAVE.setLong(4, project.getCustomer().getId());
            preparedStatementSAVE.execute();
            ResultSet resultSet = preparedStatementLastID.executeQuery();
            resultSet.next();
            id = resultSet.getLong(1);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Failed to save your project" + project.getTitle() + e);
        }
        return id;
    }

    @Override
    public Project findByID(Long id) {
        Project project = new Project(id, "", 0, new Company(0, ""), new Customer(0, ""));
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = projectBuilder(resultSet);
            if (projects.size() > 0) {
                project = projects.get(0);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Cant find your project with id = " + id);
        }
        return project;
    }

    @Override
    public void update(Project project) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setInt(2, project.getCost());
            preparedStatement.setLong(3, project.getCompany().getId());
            preparedStatement.setLong(4, project.getCustomer().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Cant find your project with id = " + project.getId() + e);
        }

    }

    @Override
    public void delete(Project project) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, project.getId());
        } catch (SQLException e) {
            System.out.println("Cant find your project with id + " + project.getId());
        }
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            projects = projectBuilder(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while trying to find all in your project :");
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Project findByName(String title) {
        Project project =
                new Project(0, "", 0, new Company(0, ""), new Customer(0, ""));
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = projectBuilder(resultSet);
            if (projects.size() > 0) {
                project = projects.get(0);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error with getting your project with name + " + title);
        }
        return project;
    }


    private List<Project> projectBuilder(ResultSet resultSet) throws SQLException {
        List<Project> projects = new ArrayList<>();
        Company company;
        Customer customer;
        long id;
        String title;
        int cost;
        while (resultSet.next()) {
            id = resultSet.getLong("id");
            title = resultSet.getString("title");
            cost = resultSet.getInt("cost");
            company = companyDAO.findByID(resultSet.getLong("company_id"));
            customer = customerDAO.findByID(resultSet.getLong("customer_id"));
            projects.add(new Project(id, title, cost, company, customer));
        }
        return projects;
    }

}
