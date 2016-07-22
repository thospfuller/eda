package com.coherentlogic.coherent.data.model.core.listeners;

import com.coherentlogic.coherent.data.model.core.builders.AbstractQueryBuilder;

/**
 * An event that is fired when an exception is thrown from within the {@link AbstractQueryBuilder}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public class QueryBuilderExceptionEvent<K, V> extends QueryBuilderEvent<K, V> {

    private static final long serialVersionUID = 3584387898541163999L;

    private final Throwable throwable;

    public QueryBuilderExceptionEvent(
        AbstractQueryBuilder<K, V> queryBuilder,
        EventType eventType,
        K key,
        V value,
        Throwable throwable,
        long operationBeganAtMillis,
        long operationAtThisStepMillis
    ) {
        super(
            queryBuilder,
            eventType,
            key,
            value,
            operationBeganAtMillis,
            operationAtThisStepMillis
        );

        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return "QueryBuilderExceptionEvent [throwable=" + throwable + ", toString()=" + super.toString() + "]";
    }
}
