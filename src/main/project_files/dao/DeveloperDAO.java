package main.project_files.dao;

import main.project_files.models.Company;
import main.project_files.models.Customer;
import main.project_files.models.Developer;
import main.project_files.models.Project;

/**
 * Interface extends methods from GenericDAO for working with data base and entities
 * Interface would be implemented by DAO classes that will work with Developer entity
 */

public interface DeveloperDAO extends GenericDAO<Developer, Long> {

    default Developer createNewEmptyDeveloper() {
        Developer developer = new Developer();
        Customer customer = new Customer(0, "");
        Company company = new Company(0, "");
        Project project = new Project(0, "", 0, company, customer);
        return new Developer(0, "", "" ,0 , company , project);
    }
}
