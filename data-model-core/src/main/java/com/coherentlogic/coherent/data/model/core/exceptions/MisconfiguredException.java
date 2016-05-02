package com.coherentlogic.coherent.data.model.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown when something is misconfigured.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MisconfiguredException extends NestedRuntimeException {

    private static final long serialVersionUID = 7348356006614794603L;

    public MisconfiguredException(String msg) {
        super(msg);
    }

    public MisconfiguredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
