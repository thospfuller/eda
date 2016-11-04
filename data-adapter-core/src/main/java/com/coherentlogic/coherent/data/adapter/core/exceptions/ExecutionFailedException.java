package com.coherentlogic.coherent.data.adapter.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * Thrown when the call to the {@link AbstractRESTQueryBuilder#doExecute} method results in an exception being thrown.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ExecutionFailedException extends NestedRuntimeException {

    private static final long serialVersionUID = 3297715949618109100L;

    public ExecutionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
