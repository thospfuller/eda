package com.coherentlogic.coherent.data.model.core.builders;

import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;
import com.coherentlogic.coherent.data.model.core.cache.NullCache;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class CacheableQueryBuilder<String, Object> {

    private final CacheServiceProviderSpecification<String, Object> cache;

    protected static final NullCache NULL_CACHE = new NullCache();

    public CacheableQueryBuilder(
        CacheServiceProviderSpecification<String, Object> cache
    ) {
        super();
        this.cache = cache;
    }

    /**
     * Getter method for the cache service provider, which may be null if one
     * was not set.
     */
    protected CacheServiceProviderSpecification<String, Object>
        getCacheServiceProvider () {
        return cache;
    }

    public CacheServiceProviderSpecification<String, Object> getCache() {
        return cache;
    }
}
