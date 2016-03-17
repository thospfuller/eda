package com.coherentlogic.coherent.data.model.core.xstream;

import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.XStream;

/**
 * Marshaller that sets the XStream marshalling strategy to an instance of
 * {@link com.coherentlogic.coherent.data.model.core.xstream.
 * CustomMarshallingStrategy}.
 *
 * @deprecated We don't really need this as we can set the marshalling strategy using a getter method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CustomXStreamMarshaller extends XStreamMarshaller {

    @Override
    protected void customizeXStream(XStream xstream) {
        super.customizeXStream(xstream);
        xstream.setMarshallingStrategy(
            new CustomMarshallingStrategy ()
        );
    }
}
