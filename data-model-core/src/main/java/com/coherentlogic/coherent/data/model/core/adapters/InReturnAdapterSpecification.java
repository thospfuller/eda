package com.coherentlogic.coherent.data.model.core.adapters;

/**
 * An adapter specification for adapters that create the source before and the actual adapt logic is responsible for
 * creating the target.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The source type.
 * @param <T> The target type.
 */
public interface InReturnAdapterSpecification<S, T> {

    T adapt (S source);
}
