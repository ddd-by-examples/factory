package pl.com.dddbyexamples.tools;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;

public interface CommandRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    @Override
    @RestResource(exported = false)
    void deleteById(ID id);

    @Override
    @RestResource(exported = false)
    void delete(T entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
