package com.coherentlogic.coherent.data.model.core.builders;

import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class CacheableQueryBuilder<K, V> {

    private final CacheServiceProviderSpecification<K, V> cache;

    public CacheableQueryBuilder(
        CacheServiceProviderSpecification<K, V> cache
    ) {
        super();
        this.cache = cache;
    }

    public CacheServiceProviderSpecification<K, V> getCache() {
        return cache;
    }
}
