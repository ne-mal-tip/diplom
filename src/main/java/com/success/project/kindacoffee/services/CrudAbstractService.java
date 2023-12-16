package com.success.project.kindacoffee.services;

import java.util.List;
import java.util.Optional;

public interface CrudAbstractService<T, I> {
    T save(T entity);

    Optional<T> find(I Id);

    List<T> findAll();

    boolean delete(I id);

}
