package pl.com.dddbyexamples.tools;

import java.util.Optional;

public interface TechnicalId {

    Long getId();

    default boolean isPersisted() {
        return getId() != null;
    }

    static Optional<Long> get(Object id) {
        return isPersisted(id) ? Optional.of(((TechnicalId) id).getId()) : Optional.empty();
    }

    static boolean isPersisted(Object id) {
        return (id instanceof TechnicalId) && ((TechnicalId) id).isPersisted();
    }
}
