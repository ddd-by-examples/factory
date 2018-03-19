package pl.com.dddbyexamples.tools;

import java.util.function.Function;
import java.util.function.Supplier;

public interface TechnicalId {

    Long getId();

    default boolean isPersisted() {
        return getId() != null;
    }

    static Long get(Object id) {
        return isPersisted(id) ? ((TechnicalId) id).getId() : null;
    }

    static boolean isPersisted(Object id) {
        return (id instanceof TechnicalId) && ((TechnicalId) id).isPersisted();
    }

    static <T> T findOrDefault(Object id, Function<Long, T> ifPresent, Supplier<T> orElse) {
        if (isPersisted(id)) {
            return ifPresent.apply(get(id));
        } else {
            return orElse.get();
        }
    }
}
