package main.project_files.factories;

import main.project_files.dao.*;
import main.project_files.dao.hibernate_DAO.*;
import main.project_files.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateFactoryDAO {
    private static SessionFactory sessionFactory;
    private static CompanyDAO companyDAO;
    private static CustomerDAO customerDAO;
    private static DeveloperDAO developerDAO;
    private static ProjectDAO projectDAO;
    private static SkillDAO skillDAO;

    private HibernateFactoryDAO() {
    }

    public static SessionFactory getSessionFactory() {
        if ((sessionFactory == null) || (sessionFactory.isClosed())) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Company.class)
                    .addAnnotatedClass(Developer.class)
                    .addAnnotatedClass(Skill.class)
                    .addAnnotatedClass(Project.class)
                    .addAnnotatedClass(Customer.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }




    public static CompanyDAO getCompanyDAO() {
        if (companyDAO == null) {
            companyDAO = new HibernateCompanyDAO(getSessionFactory());
        }
        return companyDAO;
    }

    public static CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new HibernateCustomerDAO(getSessionFactory());
        }
        return customerDAO;
    }

    public static DeveloperDAO getDeveloperDAO() {
        if (developerDAO == null) {
            developerDAO = new HibernateDeveloperDAO(getSessionFactory());
        }
        return developerDAO;
    }

    public static ProjectDAO getProjectDAO() {
        if (projectDAO == null) {
            projectDAO = new HibernateProjectDAO(getSessionFactory());
        }
        return projectDAO;
    }

    public static SkillDAO getSkillDAO() {
        if (skillDAO == null) {
            skillDAO = new HibernateSkillDAO(getSessionFactory());
        }
        return skillDAO;
    }

    public static void disconnect() {
        if ((sessionFactory != null) && (sessionFactory.isOpen())) {
            sessionFactory.close();
        }
    }
}

