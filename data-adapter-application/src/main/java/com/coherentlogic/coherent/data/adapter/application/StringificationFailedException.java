package com.coherentlogic.coherent.data.adapter.application;

/**
 * An exception that is thrown when an object cannot be converted into a String.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StringificationFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StringificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringificationFailedException(String message) {
        super(message);
    }

    public StringificationFailedException(Throwable cause) {
        super(cause);
    }
}
