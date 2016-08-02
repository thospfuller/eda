package com.coherentlogic.coherent.data.model.core.builders.soap;

/**
 * Specification for requesting data of a known type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The type of result returned from the call to {@link #doGet()}.
 */
public interface RequestMethodSpecification<T> {

    public abstract T doGet ();
}
