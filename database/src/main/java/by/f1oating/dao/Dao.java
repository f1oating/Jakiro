package by.f1oating.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<E, K> {
    public Optional<E> save(E entity);
    public boolean delete(Long id);
    public boolean update(K filter, Long id);
    public Optional<E> findById(Long id);
    public List<Optional<E>> findAll(K filter);
}
