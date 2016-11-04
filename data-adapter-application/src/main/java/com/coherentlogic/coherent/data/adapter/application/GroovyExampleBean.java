package com.coherentlogic.coherent.data.adapter.application;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.adapter.core.builders.AbstractQueryBuilder;
import com.coherentlogic.coherent.data.adapter.core.factories.TypedFactory;

public class GroovyExampleBean<Q extends TypedFactory<AbstractQueryBuilder<String, Object>>> {

    private static final Logger log = LoggerFactory.getLogger(GroovyExampleBean.class);

    private static final String QUERY_BUILDER = "queryBuilder";

    private final String scriptText;

    private final Q queryBuilderFactory;

    private final GroovyEngine groovyEngine;

    public GroovyExampleBean(
        String scriptText,
        Q queryBuilderFactory,
        GroovyEngine groovyEngine
    ) {
        this.scriptText = scriptText;
        this.queryBuilderFactory = queryBuilderFactory;
        this.groovyEngine = groovyEngine;
    }

    public Object execute (JPanel panel, String scriptText) {

        AbstractQueryBuilder<String, Object> queryBuilder = queryBuilderFactory.getInstance();

        groovyEngine.setVariable(QUERY_BUILDER, queryBuilder);

        return groovyEngine.evaluate(scriptText);
    }

    public String getScriptText() {
        return scriptText;
    }
}
