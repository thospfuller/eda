package com.coherentlogic.coherent.data.model.core.listeners;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Map;

/**
 * Event which is fired that contains all events generated from one discrete update.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AggregatePropertyChangeEvent implements Serializable {

    private static final long serialVersionUID = -7524083743818359273L;

    private final Map<String, PropertyChangeEvent> propertyChangeEventMap;

    public AggregatePropertyChangeEvent(Map<String, PropertyChangeEvent> propertyChangeEventMap) {
        this.propertyChangeEventMap = propertyChangeEventMap;
    }

    public Map<String, PropertyChangeEvent> getPropertyChangeEventMap() {
        return propertyChangeEventMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((propertyChangeEventMap == null) ? 0 : propertyChangeEventMap.hashCode());
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
        return true;
    }

    @Override
    public String toString() {
        return "AggregatePropertyChangeEvent [propertyChangeEventMap=" + propertyChangeEventMap + "]";
    }
}
