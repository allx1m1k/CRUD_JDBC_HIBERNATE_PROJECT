package main.project_files.dao;

import main.project_files.models.Skill;

/**
 * Interface extends methods from GenericDAO for working with data base and entities
 * Interface would be implemented by DAO classes that will work with SKill entity
 */

public interface SkillDAO extends GenericDAO<Skill, Long> {
}
