package com.coherentlogic.coherent.data.model.core.exceptions;

/**
 * An exception that is thrown when the framework is not configured properly.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FrameworkMisconfiguredException extends MisconfiguredException {

    private static final long serialVersionUID = 8813225924591445071L;

    public FrameworkMisconfiguredException(String msg) {
        super(msg);
    }

    public FrameworkMisconfiguredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
