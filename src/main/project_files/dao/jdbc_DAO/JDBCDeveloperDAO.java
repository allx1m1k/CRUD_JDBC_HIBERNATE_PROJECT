package main.project_files.dao.jdbc_DAO;

import main.project_files.connections.ConnectionToDB;
import main.project_files.dao.CompanyDAO;
import main.project_files.dao.DeveloperDAO;
import main.project_files.dao.ProjectDAO;
import main.project_files.factories.FactoryDAO;
import main.project_files.models.Developer;
import main.project_files.models.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JDBCDeveloperDAO implements DeveloperDAO {

    private final static String SAVE = "INSERT INTO developer(id,fname,lname,project_id,salary) VALUES(?,?,?,?,?,?)";
    private final static String SAVE_SKILLS = "INSERT into developers_skills VALUES (?,?)";
    private final static String FIND_BY_ID = "SELECT * FROM developer where id = ?";
    private final static String UPDATE_FNAME = "UPDATE developers SET fname = ?, company_id = ?,project_id = ?,salary =? WHERE id = ?";
    private final static String UPDATE_LNAME = "UPDATE developers SET lname = ?, company_id = ?,project_id = ?,salary =? WHERE id = ?";
    private final static String DELETE = "DELETE FROM developers WHERE id = ?";
    private final static String DELETE_SKILLS = "DELETE FROM developers_skills WHERE developer_id = ?";
    private final static String FIND_ALL = "SELECT * FROM developers";
    private final static String FIND_BY_FNAME = "SELECT * FROM developers WHERE fname = ?";
    private final static String FIND_BY_LNAME = "SELECT * FROM developers WHERE lname = ?";
    private final static String GET_LAST_INSERT = "SELECT LAST_INSERT_ID()";
    private final static String GET_SKILLS = "SELECT * FROM skills " +
            "JOIN developers_skills ON skills.id = developers_skills.skill_id " +
            "JOIN developers ON developers_skills.developer_id = developers.id " +
            "WHERE developers.id=?";


    private CompanyDAO companyDAO;
    private ProjectDAO projectDAO;
    private ConnectionToDB connectionToDB;

    public JDBCDeveloperDAO(ConnectionToDB connectionToDB) throws SQLException {
        this.connectionToDB = connectionToDB;
        companyDAO = FactoryDAO.getCompanyDAO();
        projectDAO = FactoryDAO.getProjectDAO();
    }


    @Override
    public Long save(Developer obj) {
        Connection connection = null;
        try {
            connection = connectionToDB.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(SAVE)) {
                statement.setString(1, obj.getFname());
                statement.setString(2, obj.getLname());
                statement.setLong(3, obj.getCompany().getId());
                statement.setLong(4, obj.getProject().getId());
                statement.setInt(5, (int) obj.getSalary());
                statement.executeUpdate();
            }
            long id;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(GET_LAST_INSERT);
                resultSet.next();
                id = resultSet.getLong(1);
            }
            try (PreparedStatement statement = connection.prepareStatement(SAVE_SKILLS)) {
                for (Skill skill : obj.getSkills()) {
                    statement.setLong(1, id);
                    statement.setLong(2, skill.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
            return id;

        } catch (Exception ex) {
            System.out.println("Cant do your transaction, rolling back");
            throw new RuntimeException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Developer findByID(Long aLong) {
        try (Connection connection = connectionToDB.getConnection()) {
            Developer developer = createNewEmptyDeveloper();
            developer.setId(aLong);
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
                preparedStatement.setLong(1, aLong);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    return developer;
                }
                developer = createDeveloper(resultSet);
            }
            HashSet<Skill> skills = createSkills(connection, developer);
            developer.setSkills(skills);
            return developer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Developer obj) {
        Connection connection = null;
        try {
            connection = connectionToDB.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_FNAME);
                 PreparedStatement statement1 = connection.prepareStatement(UPDATE_LNAME)) {
                statement1.setString(2, obj.getLname());
                statement.setString(1, obj.getFname());
                statement.setLong(3, obj.getCompany().getId());
                statement.setLong(4, obj.getProject().getId());
                statement.setLong(5, obj.getId());
                statement.setInt(6, (int) obj.getSalary());
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(DELETE_SKILLS)) {
                statement.setLong(1, obj.getId());
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(SAVE_SKILLS)) {
                for (Skill skill : obj.getSkills()) {
                    statement.setLong(1, obj.getId());
                    statement.setLong(2, skill.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        } catch (Exception ex) {
            System.out.println("Transaction failed , rolling back");
            throw new RuntimeException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void delete(Developer obj) {
        Connection connection = null;
        try {
            connection = connectionToDB.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(DELETE_SKILLS)) {
                statement.setLong(1, obj.getId());
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
                statement.setLong(1, obj.getId());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (Exception ex) {
            System.out.println("Failed transaction , rolling back");
            throw new RuntimeException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                Developer developer = createDeveloper(resultSet);
                HashSet<Skill> skills = createSkills(connection, developer);
                developer.setSkills(skills);
                developers.add(developer);
            }
            return developers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Developer findByName(String name) {
        try (Connection connection = connectionToDB.getConnection()) {
            Developer developer = createNewEmptyDeveloper();
            developer.setLname(name);
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_FNAME)) {
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    return developer;
                }
                developer = createDeveloper(resultSet);
            }
            HashSet<Skill> skills = createSkills(connection, developer);
            developer.setSkills(skills);
            return developer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer;
        developer = new Developer();
        developer.setId(resultSet.getLong("id"));
        developer.setFname(resultSet.getString("fname"));
        developer.setLname(resultSet.getString("lname"));
        developer.setSalary(resultSet.getInt("salary"));
        developer.setCompany(companyDAO.findByID(resultSet.getLong("company_id")));
        developer.setProject(projectDAO.findByID(resultSet.getLong("project_id")));
        return developer;
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private HashSet<Skill> createSkills(Connection connection, Developer developer) throws SQLException {
        HashSet<Skill> skills;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_SKILLS)) {
            preparedStatement.setLong(1, developer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            skills = new HashSet<>();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getLong("id"));
                skill.setTitle(resultSet.getString("title"));
                skills.add(skill);
            }
        }
        return skills;
    }
}
