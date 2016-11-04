package com.coherentlogic.coherent.data.adapter.core.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEvent;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEventListener;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderExceptionEvent;

/**
 * The foundation for QueryBuilder classes which the developer can use to register {@link QueryBuilderEventListener}
 * instances.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractQueryBuilder<K, V> {

    private final List<QueryBuilderEventListener<K, V>> queryBuilderEventListeners;

    public AbstractQueryBuilder() {
        this (new ArrayList<QueryBuilderEventListener<K, V>> ());
    }

    public AbstractQueryBuilder(List<QueryBuilderEventListener<K, V>> queryBuilderListeners) {
        this.queryBuilderEventListeners = queryBuilderListeners;
    }

    public List<QueryBuilderEventListener<K, V>> getQueryBuilderListeners() {
        return queryBuilderEventListeners;
    }

    @SafeVarargs
    public final void addQueryBuilderEventListener (QueryBuilderEventListener<K, V>... queryBuilderListeners) {
        this.queryBuilderEventListeners.addAll(Arrays.asList(queryBuilderListeners));
    }

    @SafeVarargs
    public final void removeQueryBuilderEventListener (QueryBuilderEventListener<K, V>... queryBuilderListeners) {
        this.queryBuilderEventListeners.removeAll(Arrays.asList(queryBuilderListeners));
    }

    protected void fireQueryBuilderEvent (QueryBuilderEvent<K, V> queryBuilderEvent) {
        for (QueryBuilderEventListener<K, V> queryBuilderListener : queryBuilderEventListeners) {
            queryBuilderListener.onEvent(queryBuilderEvent);
        }
    }

    protected void fireQueryBuilderEvent (
        QueryBuilderEvent.EventType eventType,
        K key,
        V value,
        long operationBeganAtMillis,
        long operationAtThisStepMillis
    ) {
        fireQueryBuilderEvent (
            new QueryBuilderEvent<K, V> (
                this,
                eventType,
                key,
                value,
                operationBeganAtMillis,
                operationAtThisStepMillis
            )
        );
    }

    protected void fireQueryBuilderEvent (
        QueryBuilderEvent.EventType eventType,
        K key,
        V value,
        Throwable cause,
        long operationBeganAtMillis,
        long operationAtThisStepMillis
    ) {
        fireQueryBuilderEvent (
            new QueryBuilderExceptionEvent<K, V> (
                this,
                eventType,
                key,
                value,
                cause,
                operationBeganAtMillis,
                operationAtThisStepMillis
            )
        );
    }

    /**
     * 
     * @param eventType
     * @param key
     * @param value
     * @param operationBeganAtMillis
     * @param operationAtThisStepMillis Set automatically in this method.
     */
    protected void fireQueryBuilderEvent (
        QueryBuilderEvent.EventType eventType,
        K key,
        V value,
        long operationBeganAtMillis
    ) {
        fireQueryBuilderEvent (
            eventType,
            key,
            value,
            operationBeganAtMillis,
            System.currentTimeMillis()
        );
    }

    /**
     * 
     * @param eventType
     * @param key
     * @param value
     * @param operationBeganAtMillis
     * @param operationAtThisStepMillis Set automatically in this method.
     */
    protected void fireQueryBuilderEvent (
        K key,
        V value,
        Throwable cause,
        long operationBeganAtMillis
    ) {
        fireQueryBuilderEvent (
            QueryBuilderEvent.EventType.exceptionThrown,
            key,
            value,
            cause,
            operationBeganAtMillis,
            System.currentTimeMillis()
        );
    }
}
