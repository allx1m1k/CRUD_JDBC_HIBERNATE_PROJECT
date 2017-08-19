package main.project_files.dao;

import main.project_files.models.Company;

/**
 * Interface extends methods from GenericDAO for working with data base and entities
 * Interface would be implemented by DAO classes that will work with Company entity
 */

public interface CompanyDAO extends GenericDAO<Company , Long> {
}
