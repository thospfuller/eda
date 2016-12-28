package com.coherentlogic.coherent.data.adapter.core.builders.rest;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Function;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.coherentlogic.coherent.data.adapter.core.builders.CacheableQueryBuilder;
import com.coherentlogic.coherent.data.adapter.core.builders.GetMethodSpecification;
import com.coherentlogic.coherent.data.adapter.core.cache.CacheServiceProviderSpecification;
import com.coherentlogic.coherent.data.adapter.core.cache.NullCache;
import com.coherentlogic.coherent.data.adapter.core.exceptions.ExecutionFailedException;
import com.coherentlogic.coherent.data.adapter.core.listeners.QueryBuilderEvent;
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

    private static final Logger log = LoggerFactory.getLogger(AbstractRESTQueryBuilder.class);

    private final RestTemplate restTemplate;

    private final UriBuilder uriBuilder;

    static {
        new WelcomeMessage()
            .addText("************************************************************************************************")
            .addText("***                                                                                          ***")
            .addText("***                  Welcome to the Coherent Logic Foundation Data Adapter                   ***")
            .addText("***                                version 1.0.28-RELEASE.                                   ***")
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
     * Method throws a NullPointerException if either the name or value are null; if neither reference is null a message
     * will be logged at debug level that includes the name and value.
     */
    protected AbstractRESTQueryBuilder<K> assertNotNull (String name, Object value) {

        // The uriBuilder will throw an exception if the name is null. We add
        // an additional check so that an exception is thrown if the value is
        // null. The reason for this is that the parameter should not be added
        // unless there's an appropriate value.
        if (name == null || value == null)
            throw new NullPointerException ("The name and value must both be set to non-null values (name: " + name +
                ", value: " + value + ").");
        else if (log.isDebugEnabled()) {
            log.debug("Adding the parameter with name: " + name + " and value: " + value);
        }

        return this;
    }

    /**
     * Method adds a name-value pair to the URI -- for example, assume that we're working with the URI
     * <p>
     * http://www.coherentlogic.com
     * <p>
     * calling:
     * <p>
     * addParameter("xyz", "123");
     * <p>
     * will result in:
     * <p>
     * http://www.coherentlogic.com?xyz=123
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws NullPointerException If either the name or value is null.
     */
    protected AbstractRESTQueryBuilder<K> addParameter (String name, Object value) {

        assertNotNull(name, value);

        uriBuilder.queryParam(name, value.toString());

        return this;
    }

    /**
     * Method adds a name-value date pair to the URI -- for example, assume that we're working with the URI
     * <p>
     * http://www.coherentlogic.com
     * <p>
     * calling:
     * <p>
     * addParameter("xyz", "123");
     * <p>
     * will result in:
     * <p>
     * http://www.coherentlogic.com?xyz=123
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws NullPointerException If either the name or value is null.
     */
    protected AbstractRESTQueryBuilder<K> addParameter (String name, DateFormat dateFormat, Date date) {

        assertNotNull(name, date);

        String value = dateFormat.format(date);

        uriBuilder.queryParam(name, value);

        return this;
    }

    /**
     * Method adds a name-value pair to the URI -- for example, assume that we're working with the URI
     * <p>
     * http://www.coherentlogic.com
     * <p>
     * calling:
     * <p>
     * addParameter("xyz", "123");
     * <p>
     * will result in:
     * <p>
     * http://www.coherentlogic.com?xyz=123
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws NullPointerException If either the name or value is null.
     */
    protected AbstractRESTQueryBuilder<K> addParameter (String name, Number value) {

        assertNotNull(name, value);

        uriBuilder.queryParam(name, value.toString());

        return this;
    }

    /**
     * Method adds a name-value pair to the internal list of name-value pairs.
     *
     * @param name The name of the parameter.
     * @param value The parameter value.
     *
     * @throws NullPointerException If either the name or value is null.
     */
    protected AbstractRESTQueryBuilder<K> addParameter (String name, String value) {

        assertNotNull(name, value);

        uriBuilder.queryParam(name, value);

        return this;
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
     * Method constructs the URI and first checks to see if the object currently exists in the cache -- if it does, then
     * this object is returned, otherwise the URI is called and the resultant XML is converted into an instance of type
     * <i>type</i> and the result is returned to the user.
     */
    @Override
    public <T> T doGet (Class<T> type) {

        long operationBeganAtMillis = System.currentTimeMillis();

        fireQueryBuilderEvent(QueryBuilderEvent.EventType.methodBegins, null, null, operationBeganAtMillis);

        K key = getKey ();

        T result = null;

        CacheServiceProviderSpecification<K, Object> cache = getCache();

        fireQueryBuilderEvent(
            QueryBuilderEvent.EventType.preCacheCheck,
            key,
            null,
            operationBeganAtMillis
        );

        Object object = cache.get(key);

        if (object != null && type.isInstance(object)) {

            result = (T) object;

            fireQueryBuilderEvent(
                QueryBuilderEvent.EventType.cacheHit,
                key,
                result,
                operationBeganAtMillis
            );

        } else if (object != null && !type.isInstance(object)) {

            RuntimeException exception = new ClassCastException (
                "The object " + object + " cannot be cast to type " + type + ".");

            onException(key, result, exception, operationBeganAtMillis);

        } else if (object == null) {

            try {
                result = doExecute (type);
            } catch (Throwable cause) {

                onException (
                    key,
                    null,
                    new ExecutionFailedException (
                        "The call to the doExecute method caused an exception to be thrown.",
                        cause
                    ),
                    operationBeganAtMillis
                );
            }

            cache.put(key, result);

            fireQueryBuilderEvent(QueryBuilderEvent.EventType.cacheMiss, key, result, operationBeganAtMillis);
        }

        fireQueryBuilderEvent(QueryBuilderEvent.EventType.methodEnds, key, result, operationBeganAtMillis);

        return result;
    }

    void onException (K key, Object value, RuntimeException cause, long operationBeganAtMillis) {

        log.error(cause.getMessage(), cause);

        fireQueryBuilderEvent(key, null, cause, operationBeganAtMillis);

        throw cause;
    }

    /**
     * Method executes the call to the URI returned from the call to {@link #getEscapedURI()} and returns the result as
     * a String.
     *
     * Note that this method does <i>not</i> cache responses so every call to this method will result in a call being
     * made to the web service end point.
     */
    public String doGetAsString() {
        return doGetAsString (String.class, (String result) -> { return result; });
    }

    /**
     * Method executes the call to the URI returned from the call to {@link #getEscapedURI()} and returns the result of
     * type R. Note that a new instance of RestTemplate each time this method is called.
     *
     * Note that this method does <i>not</i> cache responses so every call to this method will result in a call being
     * made to the web service end point.
     *
     * @param resultType The class of the result returned from the function.
     *
     * @param function Will be applied to the result and returns a result of type R.
     *
     * @return A result of type R.
     */
    public <R> R doGetAsString(Class<R> resultType, Function<String, R> function) {
        return doGetAsString (new RestTemplate (), resultType, function);
    }

    /**
     * Method executes the call to the URI returned from the call to {@link #getEscapedURI()} and returns the result of
     * type R.
     *
     * Note that this method does <i>not</i> cache responses so every call to this method will result in a call being
     * made to the web service end point.
     *
     * @param restTemplate The instance used to execute the GET web service call (via the getForObject method).
     *  Developers can override this method when it's necessary to use a different method (ie. postForObject).
     *
     * @param resultType The class of the result returned from the function.
     *
     * @param function Will be applied to the result and returns a result of type R.
     *
     * @return A result of type R.
     */
    protected <R> R doGetAsString(RestTemplate restTemplate, Class<R> resultType, Function<String, R> function) {

        return doGetAsString (
            restTemplate,
            (RestTemplate webMethodCall) -> {
                return webMethodCall.getForObject(getEscapedURI(), String.class);
            },
            resultType,
            function
        );
    }

    /**
     * Method executes the call to the URI returned from the call to {@link #getEscapedURI()} and then invokes the
     * specified function and returns the result of type R.
     *
     * Note that this method does <i>not</i> cache responses so every call to this method will result in a call being
     * made to the web service end point.
     *
     * @param restTemplate The instance used to execute the GET web service call (via the getForObject method).
     *  Developers can override this method when it's necessary to use a different method (ie. postForObject).
     *
     * @param webMethod A function that will execute the web method call using the restTemplate.
     *
     * @param resultType The class of the result returned from the function.
     *
     * @param function Will be applied to the result and then returns a result of type R.
     *
     * @return A result of type R.
     */
    protected <R> R doGetAsString(
        RestTemplate restTemplate,
        Function<RestTemplate, String> webMethod,
        Class<R> resultType,
        Function<String, R> function
    ) {
        log.debug("doGetAsString: method begins; restTemplate: " + restTemplate + ", webMethod: " + webMethod +
            ", resultType: " + resultType + ", function: " + function);

        String result;

        try {
            result = webMethod.apply(restTemplate);
        } catch (Exception cause) {
            throw new ExecutionFailedException (
                "The call to webMethod.apply resulted in an exception being thrown.",
                cause
            );
        }

        log.debug("doGetAsString: method ends; result: " + result);

        return function.apply(result);
    }
}
