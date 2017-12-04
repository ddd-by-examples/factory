package pl.com.bottega.tools;

public interface TechnicalId {

    Long getId();

    default boolean isPersisted() {
        return getId() != null;
    }

    static Long get(Object id) {
        return (id instanceof TechnicalId) ? ((TechnicalId) id).getId() : null;
    }

    static boolean isPersisted(Object id) {
        return (id instanceof TechnicalId) && ((TechnicalId) id).isPersisted();
    }
}
