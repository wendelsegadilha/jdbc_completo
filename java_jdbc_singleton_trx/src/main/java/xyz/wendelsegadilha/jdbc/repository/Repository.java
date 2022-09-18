package xyz.wendelsegadilha.jdbc.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic interface of CRUD
 * @param <T>
 */
public interface Repository <T> {

    List<T> findAll() throws SQLException;

    T findById(Long id) throws SQLException;

    void save(T t) throws SQLException;

    void delete (Long id) throws SQLException;
}
