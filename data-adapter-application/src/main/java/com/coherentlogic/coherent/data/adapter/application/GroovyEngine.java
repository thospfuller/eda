package com.coherentlogic.coherent.data.adapter.application;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class takes care of executing the Groovy script that is entered in the
 * input text area in the {@link FREDClientGUI}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GroovyEngine {

    private static final Logger log = LoggerFactory.getLogger(GroovyEngine.class);

    private final GroovyShell groovyShell;

    public GroovyEngine (Binding binding) {
        this (new GroovyShell (binding));
    }

    public GroovyEngine (GroovyShell groovyShell) {
        this.groovyShell = groovyShell;
    }

    public void setVariable (String name, Object value) {
        this.groovyShell.setVariable(name, value);
    }

    public Object evaluate (String scriptText) {

        log.info("scriptText: " + scriptText);

        Object result = groovyShell.evaluate(scriptText);

        return result;
    }
}
