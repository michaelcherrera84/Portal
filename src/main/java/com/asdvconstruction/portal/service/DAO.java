package com.asdvconstruction.portal.service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Michael C. Herrera
 */
public interface DAO<T> {

    /**
     * Insert a tuple.
     *
     * @param t a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    int create(T t) throws SQLException;

    /**
     * Return a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @return the requested tuple or {@code null} if the requested tuple does not exist
     * @throws SQLException if a database access error occurs
     */
    T read(Object id) throws SQLException;

    /**
     * Return a {@linkplain List} of all tuples.
     *
     * @return a {@linkplain List} of all tuples.
     * @throws SQLException if a database access error occurs
     */
    List<T> readAll() throws SQLException;

    /**
     * Update a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @param t an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    int update(Object id, T t) throws SQLException;

    /**
     * Delete a tuple.
     *
     * @param t a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    int delete(T t) throws SQLException;
}
