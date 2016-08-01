package com.coherentlogic.coherent.data.model.core.lifecycle;

/**
 * Specification for classes that should be started post-construction.
 *
 * @see {@link com.coherentlogic.coherent.data.model.core.lifecycle.Stoppable}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface Startable {

    void start ();
}
