package com.coherentlogic.coherent.data.model.core.domain;

/**
 * The specification for all beans which contain both an identity and a value.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The identity type.
 * @param <V> The value type.
 */
public interface IdentityValueSpecification<T, V>
    extends IdentitySpecification<T> {

    void setValue (V value);

    V getValue ();
}
