package main.project_files.factories;

import main.project_files.connections.ConnectionPostgreSQL;
import main.project_files.dao.*;
import main.project_files.dao.jdbc_DAO.*;

import java.sql.SQLException;

public final class FactoryDAO {
    private static CompanyDAO companyDAO;
    private static CustomerDAO customerDAO;
    private static DeveloperDAO developerDAO;
    private static ProjectDAO projectDAO;
    private static SkillDAO skillDAO;


    private FactoryDAO() {
    }


   public static CompanyDAO getCompanyDAO() throws SQLException {
        if (companyDAO == null) {
            companyDAO = createCompanyDAO();
        }
        return companyDAO;
    }

    private static CompanyDAO createCompanyDAO() throws SQLException {
        return new JDBCCompanyDAO(ConnectionPostgreSQL.getInstance());
    }


   public static CustomerDAO getCustomerDAO() throws SQLException {
        if (customerDAO == null) {
            customerDAO = createCustomerDAO();
        }
        return customerDAO;
    }

    private static CustomerDAO createCustomerDAO() throws SQLException {
        return new JDBCCustomerDAO(ConnectionPostgreSQL.getInstance().getConnection());
    }

    public static DeveloperDAO getDeveloperDAO() throws SQLException{
        if (developerDAO == null){
            developerDAO = createDeveloperDAO();
        }
        return developerDAO;
    }

    private static DeveloperDAO createDeveloperDAO() throws SQLException{
        return new JDBCDeveloperDAO(ConnectionPostgreSQL.getInstance());
    }


    public static ProjectDAO getProjectDAO() throws SQLException{
        if(projectDAO == null){
            projectDAO = createProjectDAO();
        }
        return projectDAO;
    }
    private static ProjectDAO createProjectDAO() throws SQLException {
        return new JDBCProjectDAO(ConnectionPostgreSQL.getInstance().getConnection());
    }

    public static SkillDAO getSkillDAO() throws SQLException {
        if (skillDAO == null) {
            skillDAO = createSkillDao();
        }
        return skillDAO;
    }

    private static SkillDAO createSkillDao() throws SQLException {
        return new JDBCSkillDAO(ConnectionPostgreSQL.getInstance());
    }
}
