package com.coherentlogic.coherent.data.model.core.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this class with cache providers that implement the java.util.Map interface (JBoss Infinispan and Oracle Coherence
 * are two examples).
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public class MapCompliantCacheServiceProvider<K, V>
    implements CacheServiceProviderSpecification<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MapCompliantCacheServiceProvider.class);

    private final Map<K, V> cache;

    public MapCompliantCacheServiceProvider(Map<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public V get(K key) {

        log.debug("get: method begins; key: " + key);

        V value = cache.get(key);

        log.debug("get: method ends; value: " + value);

        return value;
    }

    @Override
    public void put(K key, V value) {

        log.debug("put: method begins; key: " + key + ", value: " + value);

        cache.put(key, value);

        log.debug("put: method ends.");
    }
}
