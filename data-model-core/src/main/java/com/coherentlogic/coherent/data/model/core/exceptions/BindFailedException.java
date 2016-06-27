package com.coherentlogic.coherent.data.model.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
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
