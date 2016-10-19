package com.coherentlogic.coherent.data.model.core.builders.soap;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.coherentlogic.coherent.data.model.core.builders.CacheableQueryBuilder;
import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;
import com.coherentlogic.coherent.data.model.core.cache.NullCache;

/**
 * The foundation for working with SOAP-style web services.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractSOAPQueryBuilder<K, V>
    extends CacheableQueryBuilder<K, V>
    implements RequestMethodSpecification<V> {

    private final WebServiceTemplate webServiceTemplate;

    public AbstractSOAPQueryBuilder(WebServiceTemplate webServiceTemplate) {
        this (webServiceTemplate, new NullCache<K, V> ());
    }

    public AbstractSOAPQueryBuilder(
        WebServiceTemplate webServiceTemplate,
        CacheServiceProviderSpecification<K, V> cache
    ) {
        super(cache);
        this.webServiceTemplate = webServiceTemplate;
    }

    protected WebServiceTemplate getWebServiceTemplate () {
        return webServiceTemplate;
    }
}
