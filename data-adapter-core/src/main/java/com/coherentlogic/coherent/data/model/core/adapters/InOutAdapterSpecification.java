package com.coherentlogic.coherent.data.model.core.adapters;

/**
 * An adapter specification for adapters that create both the source and target before the actual adapt logic is
 * executed.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The source type.
 * @param <T> The target type.
 */
public interface InOutAdapterSpecification<S, T> {

    void adapt (S source, T target);
}
