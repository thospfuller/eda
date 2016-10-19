package com.coherentlogic.coherent.data.adapter.core.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEvent;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEventListener;

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
}
