package main.project_files.dao;

import main.project_files.models.Project;

/**
 * Interface extends methods from GenericDAO for working with data base and entities
 * Interface would be implemented by DAO classes that will work with Project entity
 */

public interface ProjectDAO extends GenericDAO<Project, Long> {
}
