package pl.com.bottega.tools;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CommandRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    @Override
    @RestResource(exported = false)
    void delete(ID id);

    @Override
    @RestResource(exported = false)
    void delete(T entity);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
