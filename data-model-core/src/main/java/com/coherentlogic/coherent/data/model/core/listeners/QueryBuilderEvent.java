package com.coherentlogic.coherent.data.model.core.listeners;

import java.util.EventObject;

import com.coherentlogic.coherent.data.model.core.builders.AbstractQueryBuilder;
import com.coherentlogic.coherent.data.model.core.builders.rest.AbstractRESTQueryBuilder;

/**
 * An event that is fired as the {@link AbstractRESTQueryBuilder#doExecute} method is executed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public class QueryBuilderEvent<K, V> extends EventObject {

    // operation successful or error -- if error then we include an exception.

    private static final long serialVersionUID = 2676404323369376652L;

    private final K key;

    private final long operationBeganAtMillis, operationAtThisStepMillis;

    private final V value;

    private final EventType eventType;

    /**
     * Constructor that is used to instantiate this class.
     *
     * @param queryBuilder The source where the event originates.
     * @param key The key is the url or the request which is used to get the value (via a remote call).
     * @param value The result of the call to the web service -- may be null if the invocation hasn't taken place yet.
     * @param operationBeganAtMillis The time the method call began.
     * @param operationAtThisStepMillis The current time of the operation.
     * @param eventType One of the various stages in method execution.
     */
    public QueryBuilderEvent(
        AbstractQueryBuilder<K, V> queryBuilder,
        EventType eventType,
        K key,
        V value,
        long operationBeganAtMillis,
        long operationAtThisStepMillis
    ) {
        super(queryBuilder);
        this.key = key;
        this.operationBeganAtMillis = operationBeganAtMillis;
        this.operationAtThisStepMillis = operationAtThisStepMillis;
        this.value = value;
        this.eventType = eventType;
    }

    public K getKey() {
        return key;
    }

    public long getOperationBeganAtMillis() {
        return operationBeganAtMillis;
    }

    public long getOperationAtThisStepMillis() {
        return operationAtThisStepMillis;
    }

    public V getValue() {
        return value;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return "QueryBuilderEvent [key=" + key + ", operationBeganAtMillis=" + operationBeganAtMillis
            + ", operationAtThisStepMillis=" + operationAtThisStepMillis + ", value=" + value + ", eventType="
            + eventType + "]";
    }

    public static enum EventType {
        methodBegins, methodEnds, preCache, cacheHit, cacheMiss, preInvocation, postInvocation;
    }
}
