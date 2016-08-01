package com.coherentlogic.coherent.data.model.core.xstream;

import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.data.model.core.exceptions.GenericReflectionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByXPathMarshallingStrategy;
import com.thoughtworks.xstream.core.ReferenceByXPathUnmarshaller;
import com.thoughtworks.xstream.core.TreeUnmarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * A marshalling strategy that assigns an instance of the {@link #propertyChangeSupportType} to any object which extends
 * from {@link com.coherentlogic.coherent.data.model.core.domain.SerializableBean}.
 *
 * The CustomMarshallingStrategy is set as a property on the XStreamMarshaller as follows:
 * <pre>
 * XStreamMarshaller result = new XStreamMarshaller ();
 *
 * result.setMarshallingStrategy(new CustomMarshallingStrategy ());
 * </pre>
 *
 * or in XML:
 * <pre>
 * <bean id="xstreamMarshaller" class="org.springframework.oxm.xstream.XStreamMarshaller">
 *     <property name="marshallingStrategy">
 *         <bean class="com.coherentlogic.coherent.data.model.core.xstream.CustomMarshallingStrategy"/>
 *     </property>
 *     ...
 * </bean>
 * </pre>
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CustomMarshallingStrategy extends ReferenceByXPathMarshallingStrategy {

    private static final Logger log = LoggerFactory.getLogger(CustomMarshallingStrategy.class);

    private final Class<? extends PropertyChangeSupport> propertyChangeSupportType;

    /**
     * A default constructor that sets the mode to {@link ReferenceByXPathMarshallingStrategy#RELATIVE} and the
     * {@link #propertyChangeSupportType} to {@link java.beans.PropertyChangeSupport}.
     */
    public CustomMarshallingStrategy() {
        this (RELATIVE, PropertyChangeSupport.class);
    }

    /**
     * A constructor that sets the mode to {@link ReferenceByXPathMarshallingStrategy#RELATIVE} and the
     * {@link #propertyChangeSupportType} to the propertyChangeSupportType parameter value.
     */
    public CustomMarshallingStrategy(
        Class<? extends PropertyChangeSupport> propertyChangeSupportType
    ) {
        this (RELATIVE, propertyChangeSupportType);
    }

    /**
     * A constructor that sets the mode to the value parameter value and the {@link #propertyChangeSupportType} to the
     * propertyChangeSupportType parameter value.
     */
    public CustomMarshallingStrategy(int mode, Class<? extends PropertyChangeSupport> propertyChangeSupportType) {
        super(mode);
        this.propertyChangeSupportType = propertyChangeSupportType;
    }

    /**
     * Method assigns an instance of {@link java.beans.PropertyChangeSupport} to all objects of type {@link
     * com.coherentlogic.coherent.data.model.core.domain.SerializableBean}.
     *
     * @throws NoSuchFieldException 
     *
     * @deprecated Recent changes to the {@link SerializableBean} such that the PropertyChangeSupport reference should
     *  never be null should render this unnecessary.
     */
    static void assignPropertyChangeSupport (
        Object object,
        Class<? extends PropertyChangeSupport> propertyChangeSupportType
    ) {
        if (object instanceof SerializableBean)
            assignChangeSupportInstances ((SerializableBean) object, propertyChangeSupportType);
    }

    static void setPrivateFinalFieldOn (SerializableBean targetSerializableBean, String fieldName, Object value)
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

        Field targetField = SerializableBean.class.getDeclaredField(fieldName);

        targetField.setAccessible(true);

        targetField.set(targetSerializableBean, value);

        targetField.setAccessible(false);
    }

    /**
     * Method assigns an instance of {@link java.beans.PropertyChangeSupport} to the {@link defaultObject
     * com.coherentlogic.coherent.data.model.core.domain.SerializableBean} parameter.
     *
     * @throws NoSuchFieldException 
     */
    static void assignChangeSupportInstances (
        SerializableBean serializableBean,
        Class<? extends PropertyChangeSupport> propertyChangeSupportType
    ) {
        /* From here:
         * 
         * https://coherentlogic.com/middleware-development/are-you-looking-to-retrieve-economic-data-from-the-federal-reserve-bank-of-st-louis/propertychangesupport-features/
         *
         * Note that the xstreamMarshaller bean is set to com.coherentlogic.coherent.data.model.core.xstream.
         * CustomXStreamMarshaller.
         *
         * This class allows the developer to easily switch between a standard PropertyChangeSupport (the default) and
         * javax.swing.event.SwingPropertyChangeSupport for projects that run on the desktop and utilize the Java Swing
         * API.
         */

        Constructor<? extends PropertyChangeSupport> constructor;

        try {
            // This block basically creates the instance of PropertyChangeSupport / SwingPropertyChangeSupport by
            // invoking the ctor and passing in the serializableBean.
            constructor =
                propertyChangeSupportType.getConstructor(Object.class);

            PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport) constructor.newInstance(
                serializableBean);

            setPrivateFinalFieldOn(serializableBean, "propertyChangeSupport", propertyChangeSupport);

            VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport (serializableBean);

            setPrivateFinalFieldOn(serializableBean, "vetoableChangeSupport", vetoableChangeSupport);

        } catch (NoSuchFieldException noSuchFieldException) {
            throw new GenericReflectionException("The set operation on the field has failed so this "
                + "property may have been renamed or removed altogether.", noSuchFieldException);
        } catch (
            NoSuchMethodException |
            SecurityException |
            InstantiationException |
            IllegalAccessException |
            IllegalArgumentException |
            InvocationTargetException exception
        ) {
            throw new GenericReflectionException (
                "Unable to set the propertyChangeSupport property on the " +
                "serializableBean: " + serializableBean,
                exception
            );
        }
    }

    /**
     * This method will call the super class convert method and then check the
     * resultant object type and if it is an instance of {@link
     * com.coherentlogic.coherent.data.model.core.domain.SerializableBean} an
     * instance of the {@link #propertyChangeSupportType} will be created and
     * assigned to that object.
     */
    @Override
    protected TreeUnmarshaller createUnmarshallingContext(
        Object root,
        HierarchicalStreamReader reader,
        ConverterLookup converterLookup,
        Mapper mapper
    ) {
        return new ReferenceByXPathUnmarshaller (
            root,
            reader,
            converterLookup,
            mapper
        ) {
            @Override
            protected Object convert(
                Object parent,
                Class type,
                Converter converter
            ) {
                Object result = super.convert(parent, type, converter);

                assignPropertyChangeSupport(result, propertyChangeSupportType);

                return result;
            }
        };
    }
}
