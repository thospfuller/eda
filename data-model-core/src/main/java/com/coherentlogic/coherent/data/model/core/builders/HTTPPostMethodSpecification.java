package com.coherentlogic.coherent.data.model.core.builders;

/**
 * Specification for the HTTP post method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface HTTPPostMethodSpecification {

    /**
     * Method executes an HTTP post and returns the result.
     *
     * @param type The domain class type to return. 
     *
     * @return An instance of type <i>type</i>.
     */
    <T> T doPost (Class<T> type);
}
