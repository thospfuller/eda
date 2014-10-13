package com.coherentlogic.coherent.data.model.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown when a method is not supported.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MethodNotSupportedException extends NestedRuntimeException {

    private static final long serialVersionUID = 1385600419383677564L;

	public MethodNotSupportedException(String msg) {
        super(msg);
    }

    public MethodNotSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
