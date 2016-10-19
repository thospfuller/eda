package com.coherentlogic.coherent.data.model.core.factories;

/**
 * A specification for classes that follow the factory pattern.
 *
 * @param <T> The type of object returned from the call to the {@link #getInstance()} method.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface Factory {

    public <T> T getInstance ();
}
