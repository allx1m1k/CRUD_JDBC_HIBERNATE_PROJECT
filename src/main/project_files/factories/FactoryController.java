package main.project_files.factories;

import main.project_files.controllers.*;

import java.sql.SQLException;

public final class FactoryController {

    private static CompanyController companyController;
    private static CustomerController customerController;
    private static DeveloperController developerController;
    private static ProjectController projectController;
    private static SkillController skillController;

    private FactoryController() {
    }

    public static CompanyController getCompanyController() throws SQLException {
        if (companyController == null) {
            companyController = new CompanyController(FactoryDAO.getCompanyDAO());
        }
        return companyController;
    }

    public static CustomerController getCustomerController() throws SQLException {
        if (customerController == null) {
            customerController = new CustomerController(FactoryDAO.getCustomerDAO());
        }
        return customerController;
    }

    public static DeveloperController getDeveloperController() throws SQLException {
        if (developerController == null) {
            developerController = new DeveloperController(FactoryDAO.getDeveloperDAO());
        }
        return developerController;
    }

    public static ProjectController getProjectController() throws SQLException {
        if (projectController == null) {
            projectController = new ProjectController(FactoryDAO.getProjectDAO(), HibernateFactoryDAO.getCompanyDAO(), HibernateFactoryDAO.getCustomerDAO());
        }
        return projectController;
    }

    public static SkillController getSkillController() throws SQLException {
        if (skillController == null) {
            skillController = new SkillController(FactoryDAO.getSkillDAO());
        }
        return skillController;
    }
}
