package com.coherentlogic.coherent.data.model.core.domain;

/**
 * A specification for domain classes which have an id.
 *
 * @param <T> The type of id.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface IdentitySpecification<T> {

    void setId (T id);

    T getId ();
}
