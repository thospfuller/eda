package com.coherentlogic.infinispan.cache.providers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.coherentlogic.coherent.data.adapter.core.cache.MapCompliantCacheServiceProvider;
import com.coherentlogic.coherent.data.adapter.core.factories.TypedFactory;

/**
 * An abstract cache provider template implementation for the Infinispan shared-memory data grid.
 *
 * @see <a href="http://infinispan.org/docs/stable/user_guide/user_guide.html">Infinispan User Guide</a>
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractInfinispanCacheServiceProviderFactory
    implements TypedFactory<MapCompliantCacheServiceProvider<String, Object>> {

    private final String defaultCacheName;

    private EmbeddedCacheManager cacheManager;

    public AbstractInfinispanCacheServiceProviderFactory (String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }

    /**
     * For example:
     * <p>
     * GlobalConfiguration globalConfiguration =
     *     new GlobalConfigurationBuilder()
     *         .transport()
     *         .defaultTransport()
     *         .build();
     */
    protected abstract GlobalConfiguration defineGlobalConfiguration ();

    /**
     * For example:
     * <p>
     * Configuration configuration =
     *     new ConfigurationBuilder()
     *         .read(defaultCacheConfiguration)
     *         .clustering()
     *         .cacheMode(CacheMode.DIST_SYNC)
     *         .l1()
     *         .lifespan(60000L)
     *         .build();
     */
    protected abstract Configuration defineConfiguration (Configuration defaultCacheConfiguration);

    /**
     * Method creates the {@link #cacheManager} using the GlobalConfiguration and Configuration instances.
     */
    @PostConstruct
    public void start () {

        GlobalConfiguration globalConfiguration = defineGlobalConfiguration ();

        cacheManager = new DefaultCacheManager(globalConfiguration);

        Configuration defaultCacheConfiguration = cacheManager.getDefaultCacheConfiguration();

        Configuration configuration = defineConfiguration (defaultCacheConfiguration);

        cacheManager.defineConfiguration(defaultCacheName, configuration);
    }

    @PreDestroy
    public void stop () {
        cacheManager.stop();
    }

    @Override
    public MapCompliantCacheServiceProvider<String, Object> getInstance() {
        return getInstance(defaultCacheName);
    }

    public MapCompliantCacheServiceProvider<String, Object> getInstance(String cacheName) {

        Cache<String, Object> cache = cacheManager.getCache(cacheName);

        return new MapCompliantCacheServiceProvider<String, Object> (cache);
    }
}
