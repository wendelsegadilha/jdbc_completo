package xyz.wendelsegadilha.jdbc.repository;

import java.util.List;

/**
 * Generic interface of CRUD
 * @param <T>
 */
public interface Repository <T> {

    List<T> findAll();

    T findById(Long id);

    void save(T t);

    void delete (Long id);
}
