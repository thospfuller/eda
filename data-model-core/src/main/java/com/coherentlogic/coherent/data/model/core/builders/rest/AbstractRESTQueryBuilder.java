package com.coherentlogic.coherent.data.model.core.builders.rest;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.coherentlogic.coherent.data.model.core.builders.CacheableQueryBuilder;
import com.coherentlogic.coherent.data.model.core.builders.GetMethodSpecification;
import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;
import com.coherentlogic.coherent.data.model.core.cache.NullCache;
import com.coherentlogic.coherent.data.model.core.util.WelcomeMessage;

/**
 * This class acts as the foundation for QueryBuilder implementations. This
 * class has caching capabilities where the key is the URI and the value is an
 * instance of a domain class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractRESTQueryBuilder<K> extends CacheableQueryBuilder<K, Object>
    implements GetMethodSpecification {

    private final RestTemplate restTemplate;

    private final UriBuilder uriBuilder;

    private static final Logger log = LoggerFactory.getLogger(AbstractRESTQueryBuilder.class);

    static {
        new WelcomeMessage()
            .addText("***********************************************************")
            .addText("***                                                     ***")
            .addText("*** Welcome to the Coherent Logic Foundation Data Model ***")
            .addText("***             version 1.0.21-RELEASE.                 ***")
            .addText("***                                                     ***")
            .addText("***                Follow us on LinkedIn:               ***")
            .addText("***                                                     ***")
            .addText("***       https://www.linkedin.com/company/229316       ***")
            .addText("***                                                     ***")
            .addText("***                Follow us on Twitter:                ***")
            .addText("***                                                     ***")
            .addText("***         https://twitter.com/CoherentMktData         ***")
            .addText("***                                                     ***")
            .addText("*** We   offer   support  and  consulting  services  to ***")
            .addText("*** businesses that utilize this framework or that need ***")
            .addText("*** help  with  bespoke  data  acquisition  projects -- ***")
            .addText("*** inquiries can be directed to:                       ***")
            .addText("***                                                     ***")
            .addText("*** [E] sales@coherentlogic.com                         ***")
            .addText("***                                                     ***")
            .addText("***********************************************************")
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

        uriBuilder.queryParam(name, value);//addParameter(name, value);
    }

    /**
     * Method extends the {@link #uriBuilder}'s path with the path value -- ie.
     * 
     * http://www.foo.bar/ becomes http://www.foo.bar/baz/.
     * 
     * @param path The additional path value -- in the example above, 'baz'.
     */
    protected AbstractRESTQueryBuilder extendPathWith (String path) {
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

    protected abstract K getKey ();

    protected abstract <T> T doExecute (Class<T> type);

    /**
     * Method constructs the URI and first checks to see if the object currently
     * exists in the cache -- if it does, then this object is returned, other-
     * -wise the URI is called and the resultant XML is converted into an
     * instance of type <i>type</i> and the result is returned to the user. 
     */
    @Override
    public <T> T doGet (Class<T> type) {

        K key = getKey ();

        T result = null;

        CacheServiceProviderSpecification<K, Object> cache = getCache();

        Object object = cache.get(key);

        if (object != null && type.isInstance(object))
            result = (T) object;
        else if (object != null && !type.isInstance(object))
            throw new ClassCastException (
                "The object " + object + " cannot be cast to type " + type + ".");
        else if (object == null) {
            result = doExecute (type);
            cache.put(key, result);
        }
        return result;
    }
}
