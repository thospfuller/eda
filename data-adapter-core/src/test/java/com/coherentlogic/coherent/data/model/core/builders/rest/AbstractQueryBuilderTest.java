package com.coherentlogic.coherent.data.model.core.builders.rest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;

/**
 * Unit test for the {@link AbstractRESTQueryBuilder} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AbstractQueryBuilderTest {

    private static final String TEST = "test",
        TEST_COM = "http://www.test.com/";

    private AbstractRESTQueryBuilder queryBuilder = null;

    @Before
    public void setUp() throws Exception {
        queryBuilder = new TestQueryBuilder (null, TEST_COM);
    }

    @After
    public void tearDown() throws Exception {
        queryBuilder = null;
    }

    /**
     * @todo Test for when an exception is thrown.
     */
    @Test
    public void testQueryBuilderEventListener() {

        AtomicLong ctr = new AtomicLong ();

        queryBuilder.addQueryBuilderEventListener(
            event -> {
                ctr.incrementAndGet();
            }
        );

        queryBuilder.doGet(Object.class);

        assertEquals(4, ctr.get());
    }

    @Test(expected=NullPointerException.class)
    public void testPutWithNullName() {
        queryBuilder.addParameter(null, TEST);
    }

    @Test(expected=NullPointerException.class)
    public void testPutWithNullValue1() {
        queryBuilder.addParameter(TEST, (String) null);
    }

    @Test(expected=NullPointerException.class)
    public void testPutWithNullValue2() {
        queryBuilder.addParameter(TEST, (BigDecimal) null);
    }

    @Test
    public void testPutWithValidValues() {
        queryBuilder.addParameter(TEST, TEST);

        String escapedUri = queryBuilder.getEscapedURI();

        assertEquals ("http://www.test.com/?test=test", escapedUri);
    }

    @Test
    public void testCacheFunctionalityWhereObjectHasBeenCached () {

        CacheServiceProviderSpecification<String, Object> cache =
            new CacheServiceProvider();

        TestQueryBuilder testQueryBuilder =
            new TestQueryBuilder(null, TEST_COM, cache);

        testQueryBuilder
            .foo()
            .setBar(TestQueryBuilder.BAZ);

        String escapedURI = testQueryBuilder.getEscapedURI();

        Object expected = new Object ();

        cache.put(escapedURI, expected);

        Object actual = testQueryBuilder.doGet(Object.class);

        assertEquals(expected, actual);
    }

    @Test(expected=ClassCastException.class)
    public void testCacheFunctionalityWithWrongType () {

        CacheServiceProviderSpecification<String, Object> cache =
            new CacheServiceProvider();

        TestQueryBuilder testQueryBuilder =
            new TestQueryBuilder(null, TEST_COM, cache);

        testQueryBuilder
            .foo()
            .setBar(TestQueryBuilder.BAZ);

        String escapedURI = testQueryBuilder.getEscapedURI();

        Object expected = new Integer ("6");

        cache.put(escapedURI, expected);

        // This should work.
        Object actual = testQueryBuilder.doGet(Integer.class);

        // We're trying to cast an Integer to an String and this won't work as
        // it's the wrong type.
        actual = testQueryBuilder.doGet(String.class);
    }

//    /**
//     * In this test we check to see if the object is added to the cache when we
//     * invoke the doGet method.
//     */
//    @Test
//    public void testCacheFunctionalityWhereObjectHasNotBeenCached () {
//
//        CacheServiceProviderSpecification<String, Object> cache =
//            new CacheServiceProvider();
//
//        RestTemplate restTemplate = mock (RestTemplate.class);
//
//        Object expected = new Object ();
//
//        when (
//            restTemplate.getForObject(
//                any(String.class),
//                any(Class.class)
//            )
//        ).thenReturn(expected);
//
//        TestQueryBuilder testQueryBuilder =
//            new TestQueryBuilder(restTemplate, TEST_COM, cache);
//
//        testQueryBuilder
//            .foo()
//            .setBar(TestQueryBuilder.BAZ);
//
//        String escapedURI = testQueryBuilder.getEscapedURI();
//
//        testQueryBuilder.doGet(Object.class);
//
//        // The object should be in the cache now.
//        Object actual = cache.get(escapedURI);
//
//        assertEquals(expected, actual);
//    }
}

class CacheServiceProvider
    implements CacheServiceProviderSpecification<String, Object> {

    private final Map<String, Object> cache = new HashMap<String, Object> ();
    
    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }
}

class TestQueryBuilder extends AbstractRESTQueryBuilder<String> {

    public static final String FOO = "foo", BAR = "bar", BAZ = "baz";

    protected TestQueryBuilder(RestTemplate restTemplate, String uri) {
        super (restTemplate, uri);
    }

    protected TestQueryBuilder(
        RestTemplate restTemplate,
        String uri,
        CacheServiceProviderSpecification<String, Object> cache
    ) {
        super(restTemplate, uri, cache);
    }

    /**
     * Extends the path to include 'foo' -- for example:
     *
     * http://www.foobar.zzz/foo/
     */
    public TestQueryBuilder foo () {

        extendPathWith(FOO);

        return this;
    }

    /**
     * Setter method for the bar parameter.
     */
    public TestQueryBuilder setBar (String bar) {

        addParameter(BAR, bar);

        return this;
    }

    @Override
    protected String getKey() {
        return getEscapedURI();
    }

    @Override
    protected <T> T doExecute(Class<T> type) {
        // throw new UnsupportedOperationException();
        return null;
    }
}