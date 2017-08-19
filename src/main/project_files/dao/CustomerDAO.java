package main.project_files.dao;

import main.project_files.models.Customer;

/**
 * Interface extends methods from GenericDAO for working with data base and entities
 * Interface would be implemented by DAO classes that will work with Customer entity
 */

public interface CustomerDAO extends GenericDAO<Customer, Long> {
}
