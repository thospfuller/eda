package com.coherentlogic.coherent.data.model.core.listeners;

/**
 * A listener for events that are fired by the {@link QueryBuilder}.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
@FunctionalInterface
public interface QueryBuilderEventListener<K, V> {

    void onEvent (QueryBuilderEvent<K, V> event);
}
