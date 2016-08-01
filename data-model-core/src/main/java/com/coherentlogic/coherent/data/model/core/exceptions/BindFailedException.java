package com.coherentlogic.coherent.data.model.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown when an object could not be bound to the JNDI registry.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class BindFailedException extends NestedRuntimeException {

    private static final long serialVersionUID = -1583833692829916851L;

    public BindFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BindFailedException(String msg) {
        super(msg);
    }

    public BindFailedException(String key, Object value, Throwable cause) {
        super("Unable to bind the key '" + key + "' and value " + value, cause);
    }
}
