package org.plenkovii.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T save(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> findAll();
    Optional<T> findById(int id);

}