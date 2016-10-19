package com.coherentlogic.coherent.data.adapter.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that indicates there was a problem marshalling or unmarshalling XML to or from an object.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ConversionFailedException extends NestedRuntimeException {

    private static final long serialVersionUID = 7010310727728750847L;

    public ConversionFailedException(String msg) {
        super(msg);
    }

    public ConversionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
