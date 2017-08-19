package main.project_files.dao.jdbc_DAO;

import main.project_files.connections.ConnectionToDB;
import main.project_files.dao.SkillDAO;
import main.project_files.models.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSkillDAO implements SkillDAO {

    private final static String SAVE = "INSERT INTO skills(title) VALUES(?)";
    private final static String FIND_BY_ID = "SELECT * FROM skills where id = ?";
    private final static String UPDATE = "UPDATE skills SET title = ? WHERE id = ?";
    private final static String DELETE = "DELETE * FROM skills WHERE id = ?";
    private final static String FIND_ALL = "SELECT * FROM skills";
    private final static String FIND_BY_TITLE = "SELECT * FROM skills where title = ?";
    private final static String GET_LAST_INSERT = "SELECT LAST_INSERT_ID()";


    private ConnectionToDB connectionToDB;

    public JDBCSkillDAO(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    @Override
    public Long save(Skill obj) {
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
    public Skill findByID(Long aLong) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return new Skill(aLong, "");
            }
            return createSkill(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Skill obj) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, obj.getTitle());
            preparedStatement.setLong(2, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Skill obj) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                Skill skill = createSkill(resultSet);
                skills.add(skill);
            }
            return skills;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill findByName(String title) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return new Skill(0, title);
            }
            return createSkill(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Skill createSkill(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill();
        skill.setId(resultSet.getLong("id"));
        skill.setTitle(resultSet.getString("title"));
        return skill;
    }
}
