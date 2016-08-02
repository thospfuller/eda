package com.coherentlogic.coherent.data.model.core.xstream;

import static org.junit.Assert.assertNotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;

/**
 * Unit test for the {@link CustomMarshallingStrategy} class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class CustomMarshallingStrategyTest {

    static class TestSerializableBean extends SerializableBean {

        private String foo;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) throws PropertyVetoException {

            String oldValue = this.foo;

            this.foo = foo;

            firePropertyChange("foo", oldValue, foo);
            fireVetoableChange("foo", oldValue, foo);
        }
    }

    @Test
    public void testAssignPropertyChangeSupportSerializableBeanClassOfQextendsPropertyChangeSupport()
        throws PropertyVetoException {

        TestSerializableBean fooBean = new TestSerializableBean ();

        CustomMarshallingStrategy.assignChangeSupportInstances(
            (SerializableBean) fooBean,
            PropertyChangeSupport.class
        );

        assertNotNull (fooBean.getPropertyChangeSupport());
        assertNotNull (fooBean.getVetoableChangeSupport());

        // The following should not result in an NPE, which is what we're watching for here.

        fooBean.addVetoableChangeListener(
            new VetoableChangeListener() {

            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
            }
        });

        fooBean.setFoo("bar");
    }
}
