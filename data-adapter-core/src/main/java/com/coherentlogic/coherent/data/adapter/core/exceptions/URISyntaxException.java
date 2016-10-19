package com.coherentlogic.coherent.data.adapter.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown when the URI syntax is invalid.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class URISyntaxException extends NestedRuntimeException {

    private static final long serialVersionUID = 8765128986938151721L;

    public URISyntaxException(String msg) {
        super(msg);
    }

    public URISyntaxException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
