package com.coherentlogic.coherent.data.model.core.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.coherentlogic.coherent.data.model.core.listeners.QueryBuilderListener;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractQueryBuilder<K, V> {

    private final List<QueryBuilderListener<K, V>> queryBuilderListeners;

    public AbstractQueryBuilder() {
        this (new ArrayList<QueryBuilderListener<K, V>> ());
    }

    public AbstractQueryBuilder(List<QueryBuilderListener<K, V>> queryBuilderListeners) {
        this.queryBuilderListeners = queryBuilderListeners;
    }

    public List<QueryBuilderListener<K, V>> getQueryBuilderListeners() {
        return queryBuilderListeners;
    }

    @SafeVarargs
    public final void addQueryBuilderListener (QueryBuilderListener<K, V>... queryBuilderListeners) {
        this.queryBuilderListeners.addAll(Arrays.asList(queryBuilderListeners));
    }

    @SafeVarargs
    public final void removeQueryBuilderListener (QueryBuilderListener<K, V>... queryBuilderListeners) {
        this.queryBuilderListeners.removeAll(Arrays.asList(queryBuilderListeners));
    }
}
