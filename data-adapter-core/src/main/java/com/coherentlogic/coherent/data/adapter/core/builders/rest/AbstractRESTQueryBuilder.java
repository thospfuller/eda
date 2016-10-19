package com.coherentlogic.coherent.data.adapter.core.builders.rest;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.web.client.RestTemplate;

import com.coherentlogic.coherent.data.adapter.core.builders.CacheableQueryBuilder;
import com.coherentlogic.coherent.data.adapter.core.builders.GetMethodSpecification;
import com.coherentlogic.coherent.data.adapter.core.cache.CacheServiceProviderSpecification;
import com.coherentlogic.coherent.data.adapter.core.cache.NullCache;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEvent;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderExceptionEvent;
import com.coherentlogic.coherent.data.adapter.core.util.WelcomeMessage;

/**
 * This class acts as the foundation for QueryBuilder implementations. This class has caching capabilities where the key
 * is the URI and the value is an instance of a domain class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractRESTQueryBuilder<K> extends CacheableQueryBuilder<K, Object>
    implements GetMethodSpecification {

    private final RestTemplate restTemplate;

    private final UriBuilder uriBuilder;

    static {
        new WelcomeMessage()
            .addText("************************************************************************************************")
            .addText("***                                                                                          ***")
            .addText("***                  Welcome to the Coherent Logic Foundation Data Adapter                   ***")
            .addText("***                                version 1.0.25-RELEASE.                                   ***")
            .addText("***                                                                                          ***")
            .addText("***                     Please  take a moment to follow us on LinkedIn:                      ***")
            .addText("***                                                                                          ***")
            .addText("***                         https://www.linkedin.com/company/229316                          ***")
            .addText("***                                                                                          ***")
            .addText("***                                  or  on Twitter:                                         ***")
            .addText("***                                                                                          ***")
            .addText("***                         https://twitter.com/CoherentMktData                              ***")
            .addText("***                                                                                          ***")
            .addText("*** We offer support and consulting services  to  businesses  that  utilize  this  framework ***")
            .addText("*** framework or that need help  with  bespoke  data  acquisition  projects -- inquiries can ***")
            .addText("*** be directed to:                                                                          ***")
            .addText("***                                                                                          ***")
            .addText("*** [E] sales@coherentlogic.com                                                              ***")
            .addText("*** [T] +1.571.306.3403 (GMT-5)                                                              ***")
            .addText("***                                                                                          ***")
            .addText("************************************************************************************************")
        .display();
    }

    /**
     * Method returns a new instance of {@link UriBuilder} created using the
     * value of the URI.
     */
    static UriBuilder newUriBuilder (String uri) {
        UriBuilder builder;

        builder = UriBuilder.fromPath(uri);

        return builder;
    }

    protected AbstractRESTQueryBuilder (
        RestTemplate restTemplate,
        String uri
    ) {
        this (restTemplate, newUriBuilder (uri));
    }

    protected AbstractRESTQueryBuilder (
        RestTemplate restTemplate,
        UriBuilder uriBuilder
    ) {
        this (restTemplate, uriBuilder, new NullCache<K, Object> ());
    }

    protected AbstractRESTQueryBuilder (
        RestTemplate restTemplate,
        String uri,
        CacheServiceProviderSpecification<K, Object> cache
    ) {
        this (restTemplate, newUriBuilder (uri), cache);
    }

    protected AbstractRESTQueryBuilder (
        RestTemplate restTemplate,
        UriBuilder uriBuilder,
        CacheServiceProviderSpecification<K, Object> cache
    ) {
        super (cache);
        this.restTemplate = restTemplate;
        this.uriBuilder = uriBuilder;
    }

    /**
     * Method adds a name-value pair to the internal list of name-value pairs.
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws IllegaStateException If either the name or value is null.
     */
    protected void addParameter (String name, String value) {

        // The uriBuilder will throw an exception if the name is null. We add
        // an additional check so that an exception is thrown if the value is
        // null. The reason for this is that the parameter should not be added
        // unless there's an appropriate value.
        if (name == null || value == null)
            throw new NullPointerException ("The name and value must " +
                "both be set to non-null values (name: " + name + ", value: " +
                value + ").");

        uriBuilder.queryParam(name, value);
    }

    // TODO: Consider implementing the following method.
    // protected void addParameter (String name, Object value)

    /**
     * Method adds a name-value pair to the internal list of name-value pairs.
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws IllegaStateException If either the name or value is null.
     */
    protected void addParameter (String name, Number value) {

        // The uriBuilder will throw an exception if the name is null. We add
        // an additional check so that an exception is thrown if the value is
        // null. The reason for this is that the parameter should not be added
        // unless there's an appropriate value.
        if (name == null || value == null)
            throw new NullPointerException ("The name and value must " +
                "both be set to non-null values (name: " + name + ", value: " +
                value + ").");

        uriBuilder.queryParam(name, value.toString());
    }

    /**
     * Method extends the {@link #uriBuilder}'s path with the path value -- ie.
     *
     * http://www.foo.bar/ becomes http://www.foo.bar/baz/.
     *
     * @param path The additional path value -- in the example above, 'baz'.
     */
    protected AbstractRESTQueryBuilder<K> extendPathWith (String path) {
        uriBuilder.path(path);

        return this;
    }

    /**
     * Method returns the escaped URI that is actually send to the World Bank
     * web service when the execute method has been called.
     *
     * This method can be useful when debugging, just print the escaped URI and
     * paste it into the address bar in your browser and see what's returned.
     *
     * @return A sting representation of the escaped URI.
     *
     * @todo This method needs to go in a parent class.
     */
    public String getEscapedURI () {

        URI uri = uriBuilder.build();

        String escapedURI = uri.toASCIIString();

        return escapedURI;
    }

    /**
     * Getter method for the RestTemplate.
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * Getter method for the RestTemplate.
     */
    protected UriBuilder getUriBuilder() {
        return uriBuilder;
    }

    /**
     * Method is used to get the key for this instance of the {@link AbstractRESTQueryBuilder} -- the key is then used
     * to lookup the value in the cache. If the value exists then it is returned to the user, otherwise the call to the
     * web service will be executed.
     */
    protected abstract K getKey ();

    /**
     * Use the {@link #restTemplate} to execute the call to the web service -- this can be a get or post operation, etc.
     */
    protected abstract <T> T doExecute (Class<T> type);

    /**
     * Method constructs the URI and first checks to see if the object currently
     * exists in the cache -- if it does, then this object is returned, other-
     * -wise the URI is called and the resultant XML is converted into an
     * instance of type <i>type</i> and the result is returned to the user.
     */
    @Override
    public <T> T doGet (Class<T> type) {

        long operationBeganAtMillis = System.currentTimeMillis();

        fireQueryBuilderEvent(
            new QueryBuilderEvent<K, Object>(
                this,
                QueryBuilderEvent.EventType.methodBegins,
                null,
                null,
                operationBeganAtMillis, operationBeganAtMillis
            )
        );

        K key = getKey ();

        T result = null;

        CacheServiceProviderSpecification<K, Object> cache = getCache();

        fireQueryBuilderEvent(
            new QueryBuilderEvent<K, Object>(
                this,
                QueryBuilderEvent.EventType.preCache,
                key,
                null,
                operationBeganAtMillis, System.currentTimeMillis()
            )
        );

        Object object = cache.get(key);

        if (object != null && type.isInstance(object)) {

            result = (T) object;

            fireQueryBuilderEvent(
                new QueryBuilderEvent<K, Object>(
                    this,
                    QueryBuilderEvent.EventType.cacheHit,
                    key,
                    result,
                    operationBeganAtMillis, System.currentTimeMillis()
                )
            );

        } else if (object != null && !type.isInstance(object)) {

            RuntimeException exception = new ClassCastException (
                "The object " + object + " cannot be cast to type " + type + ".");

            fireQueryBuilderEvent(
                new QueryBuilderExceptionEvent<K, Object>(
                    this,
                    QueryBuilderEvent.EventType.methodEnds,
                    key,
                    result,
                    exception,
                    operationBeganAtMillis, System.currentTimeMillis()
                )
            );

            throw exception;

        } else if (object == null) {

            result = doExecute (type);

            cache.put(key, result);

            fireQueryBuilderEvent(
                new QueryBuilderEvent<K, Object>(
                    this,
                    QueryBuilderEvent.EventType.cacheMiss,
                    key,
                    result,
                    operationBeganAtMillis, System.currentTimeMillis()
                )
            );
        }

        fireQueryBuilderEvent(
            new QueryBuilderEvent<K, Object>(
                this,
                QueryBuilderEvent.EventType.methodEnds,
                key,
                result,
                operationBeganAtMillis, System.currentTimeMillis()
            )
        );

        return result;
    }
}
