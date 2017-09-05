package main.project_files.factories;

import main.project_files.controllers.*;

public final class HibernateFactoryController {
    private static CompanyController companyController;
    private static CustomerController customerController;
    private static DeveloperController developerController;
    private static ProjectController projectController;
    private static SkillController skillController;


    private HibernateFactoryController() {
    }

    public static CompanyController getCompanyController() {
        if (companyController == null) {
            companyController = new CompanyController(HibernateFactoryDAO.getCompanyDAO());
        }
        return companyController;
    }

    public static CustomerController getCustomerController() {
        if (customerController == null) {
            customerController = new CustomerController(HibernateFactoryDAO.getCustomerDAO());
        }
        return customerController;
    }

    public static DeveloperController getDeveloperController() {
        if (developerController == null) {
            developerController = new DeveloperController(
                    HibernateFactoryDAO.getDeveloperDAO(),
                    HibernateFactoryDAO.getSkillDAO(),
                    HibernateFactoryDAO.getCompanyDAO(),
                    HibernateFactoryDAO.getProjectDAO()
            );
        }
        return developerController;
    }

    public static ProjectController getProjectController() {
        if (projectController == null) {
            projectController = new ProjectController(
                    HibernateFactoryDAO.getProjectDAO(),
                    HibernateFactoryDAO.getCompanyDAO(),
                    HibernateFactoryDAO.getCustomerDAO()
            );
        }
        return projectController;
    }

    public static SkillController getSkillController() {
        if (skillController == null) {
            skillController = new SkillController(HibernateFactoryDAO.getSkillDAO());
        }
        return skillController;
    }
}

