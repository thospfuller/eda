package com.coherentlogic.coherent.data.model.core.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.coherentlogic.coherent.data.model.core.listeners.QueryBuilderEvent;
import com.coherentlogic.coherent.data.model.core.listeners.QueryBuilderEventListener;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractQueryBuilder<K, V> {

    private final List<QueryBuilderEventListener<K, V>> queryBuilderListeners;

    public AbstractQueryBuilder() {
        this (new ArrayList<QueryBuilderEventListener<K, V>> ());
    }

    public AbstractQueryBuilder(List<QueryBuilderEventListener<K, V>> queryBuilderListeners) {
        this.queryBuilderListeners = queryBuilderListeners;
    }

    public List<QueryBuilderEventListener<K, V>> getQueryBuilderListeners() {
        return queryBuilderListeners;
    }

    @SafeVarargs
    public final void addQueryBuilderListener (QueryBuilderEventListener<K, V>... queryBuilderListeners) {
        this.queryBuilderListeners.addAll(Arrays.asList(queryBuilderListeners));
    }

    @SafeVarargs
    public final void removeQueryBuilderListener (QueryBuilderEventListener<K, V>... queryBuilderListeners) {
        this.queryBuilderListeners.removeAll(Arrays.asList(queryBuilderListeners));
    }

    protected void fireQueryBuilderEvent (QueryBuilderEvent<K, V> queryBuilderEvent) {
        for (QueryBuilderEventListener<K, V> queryBuilderListener : queryBuilderListeners) {
            queryBuilderListener.onEvent(queryBuilderEvent);
        }
    }
}
