package be.tsapasmi33.digitalcityairport.services;

import java.util.List;

public interface CrudService<T, K> {
    List<T> getAll();
    T getOne(K id);
    void insert(T entity);
    void delete(K id);
    T update(K id, T entity);
}
