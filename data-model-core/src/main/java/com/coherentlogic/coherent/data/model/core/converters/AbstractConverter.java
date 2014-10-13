package com.coherentlogic.coherent.data.model.core.converters;

import com.coherentlogic.coherent.data.model.core.exceptions.MethodNotSupportedException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A read-only converter with a default implementation of the {@link
 * #marshal(Object, HierarchicalStreamWriter, MarshallingContext)} method which
 * throws a MethodNotSupportedException when invoked.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractConverter implements Converter {

    /**
     * @throws MethodNotSupportedException This method will not be implemented.
     */
    @Override
    public void marshal(
        Object source,
        HierarchicalStreamWriter writer,
        MarshallingContext context) {
        throw new MethodNotSupportedException(
            "The marshal method is not supported.");
    }
}
