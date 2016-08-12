package com.coherentlogic.coherent.data.model.core.listeners;

import java.util.EventListener;

/**
 * Specification that is implemented when listening for aggregate property change event notifications.
 *
 * @param <S> The aggregatePropertyChangeEvent source type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@FunctionalInterface
public interface AggregatePropertyChangeListener<S> extends EventListener {

    void onAggregatePropertyChangeEvent (AggregatePropertyChangeEvent<S> aggregatePropertyChangeEvent);
}
