package com.coherentlogic.coherent.data.model.core.listeners;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Map;

/**
 * Event which is fired that contains all events generated from one discrete update.
 *
 * @param <S> The source type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AggregatePropertyChangeEvent<S> implements Serializable {

    private static final long serialVersionUID = 7169677559654674929L;

    private final S source;

    private final Map<String, PropertyChangeEvent> propertyChangeEventMap;

    public AggregatePropertyChangeEvent(S source, Map<String, PropertyChangeEvent> propertyChangeEventMap) {
        this.source = source;
        this.propertyChangeEventMap = propertyChangeEventMap;
    }

    /**
     * Getter method for the source, which caused this event to be fired.
     */
    public S getSource() {
        /* Note that we could take the source from any of the PropertyChangeEvent instances and it will suffice but this
         * isn't really clean (IMO) hence we've added this method here..
         */
        return source;
    }

    public Map<String, PropertyChangeEvent> getPropertyChangeEventMap() {
        return propertyChangeEventMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((propertyChangeEventMap == null) ? 0 : propertyChangeEventMap.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AggregatePropertyChangeEvent other = (AggregatePropertyChangeEvent) obj;
        if (propertyChangeEventMap == null) {
            if (other.propertyChangeEventMap != null)
                return false;
        } else if (!propertyChangeEventMap.equals(other.propertyChangeEventMap))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AggregatePropertyChangeEvent [source=" + source + ", propertyChangeEventMap=" + propertyChangeEventMap
            + "]";
    }
}
