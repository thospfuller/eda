package com.coherentlogic.coherent.data.model.core.builders;

/**
 * Specification for the get method that returns data from the call to the web service end point.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/servlet/http/
 *  HttpServlet.html">javax.servlet.http.HttpServlet</a>
 *
 * @see <a href="http://static.springsource.org/spring/docs/3.0.x/api/org/
 *  springframework/web/client/RestTemplate.html">RestTemplate</a>
 */
public interface GetMethodSpecification {

    /**
     * Method executes an HTTP get and returns the result.
     *
     * @param type The domain class type to return. 
     *
     * @return An instance of type <i>type</i>.
     */
    <T> T doGet (Class<T> type);
}
