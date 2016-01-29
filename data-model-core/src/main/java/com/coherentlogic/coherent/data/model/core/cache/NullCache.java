package com.coherentlogic.coherent.data.model.core.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cache service provider implementation which is used when no caching is configured.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class NullCache<K, V>
    implements CacheServiceProviderSpecification<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MapCompliantCacheServiceProvider.class);

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {
        log.debug (this + " is in use so the put operation for key: " + key + " and value: " + value + " will be "
            + "ignored.");
    }
}
