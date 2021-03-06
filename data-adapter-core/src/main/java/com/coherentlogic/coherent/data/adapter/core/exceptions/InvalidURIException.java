package com.coherentlogic.coherent.data.adapter.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown when a URI cannot be created.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidURIException extends NestedRuntimeException {

    private static final long serialVersionUID = -6529739733624873547L;

    public InvalidURIException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidURIException(String msg) {
        super(msg);
    }
}
