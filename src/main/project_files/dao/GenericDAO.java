package main.project_files.dao;

import java.io.Serializable;
import java.util.List;


/**
 * Interface with key methods for database
 */

public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Method for saving a new entity in database
     *
     * @return id of saved entity
     */

    ID save(T obj);

    /**
     * Method for finding entity in database
     *
     * @return founded an entity
     */

    T findByID(ID id);

    /**
     * Method for updating data in database
     */

    void update(T obj);

    /**
     * Method for deleting data from database
     */

    void delete(T obj);

    /**
     * Method for getting all entities of exactly one type
     *
     * @return list of entities with one type
     */

    List<T> findAll();

    /**
     * Method for finding entity in database by name
     *
     * @return founded entity
     */

    T findByName(String name);
}
