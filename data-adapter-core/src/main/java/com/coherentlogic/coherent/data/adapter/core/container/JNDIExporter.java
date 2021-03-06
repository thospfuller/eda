package com.coherentlogic.coherent.data.adapter.core.container;

import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jndi.JndiTemplate;

import com.coherentlogic.coherent.data.adapter.core.exceptions.BindFailedException;

/**
 * Class is used to register an object, by key, with the Java Naming and Directory Interface (JNDI).
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JNDIExporter implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(JNDIExporter.class);

    private final JndiTemplate jndiTemplate;

    private final Map<String, Object> jndiMapping;

    public JNDIExporter(Map<String, Object> jndiMapping) {
        this(new JndiTemplate(), jndiMapping);
    }

    public JNDIExporter(JndiTemplate jndiTemplate, Map<String, Object> jndiMapping) {
        this.jndiTemplate = jndiTemplate;
        this.jndiMapping = jndiMapping;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        for(Entry<String, Object> target: jndiMapping.entrySet()){

            String key = target.getKey();
            Object value = target.getValue();

            export (key, value);
        }
    }

    public JNDIExporter export (String key, Object value) {

        log.info("export: method begins; key: " + key + ", value '" + value);

        try {
            jndiTemplate.bind(key, value);
        } catch (NamingException namingException) {
            throw new BindFailedException(key, value, namingException);
        }

        log.info("export: method ends.");

        return this;
    }
}
