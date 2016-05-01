package com.coherentlogic.coherent.data.model.core.factories;

/**
 * Generic interface for classes that follow the factory pattern.
 *
 * @param <T> The type of object the getInstance method returns.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface TypedFactory<T> {

    T getInstance ();
}
