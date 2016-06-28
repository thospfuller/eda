package com.coherentlogic.coherent.data.model.core.domain;

import static org.junit.Assert.*;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.data.model.core.exceptions.MisconfiguredException;

/**
 * Unit test for the {@link PropertyChangeEventGeneratorAspect} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PropertyChangeEventGeneratorAspectTest {

    private PropertyChangeEventGeneratorAspect aspect = null;

    private Bar bar = null;

    private Foo foo = null;

    private AtomicBoolean flag = null;

    @Before
    public void setUp() throws Exception {
        aspect = new PropertyChangeEventGeneratorAspect<Foo> (Foo.class);
        foo = new Foo ();
        bar = new Bar ();
        flag = new AtomicBoolean (false);
    }

    @After
    public void tearDown() throws Exception {
        aspect = null;
        foo = null;
        bar = null;
        flag = null;
    }

    @Test
    public void testInvokeHappyPath() throws Throwable {

        foo.addPropertyChangeListener(
            event -> {

                assertEquals (foo, event.getSource());
                assertEquals (Foo.FI, event.getPropertyName());
                assertNull (event.getOldValue());
                assertEquals (BigDecimal.TEN, event.getNewValue());

                flag.set(true);
            }
        );

        MethodInvocation methodInvocation = new FooMethodInvocation (foo, "setFi");

        aspect.invoke(methodInvocation);

        assertTrue (flag.get());
    }

    @Test
    public void testInvokeAsSerializableBeanHappyPath() throws Throwable {
        assertNotNull (aspect.asSerializableBean(new Foo ()));
    }

    @Test(expected=MisconfiguredException.class)
    public void testInvokeAsSerializableBeanWithWrongBean() throws Throwable {
        assertNotNull (aspect.asSerializableBean(new Bar ()));
    }

    @Test(expected=NullPointerException.class)
    public void testInvokeAsSerializableBeanWithNullBean() throws Throwable {
        assertNotNull (aspect.asSerializableBean(null));
    }

    @Test(expected=MisconfiguredException.class)
    public void testInvokeWithNonSerializableBean () throws Throwable {

        foo.addPropertyChangeListener(
            event -> {
                fail ("The propertyChangeListener method should not have been called.");
            }
        );

        MethodInvocation methodInvocation = new BarMethodInvocation (bar);

        aspect.invoke(methodInvocation);
    }

    @Test(expected=NullPointerException.class)
    public void testInvokeWithNNullTarget () throws Throwable {

        foo.addPropertyChangeListener(
            event -> {
                fail ("The propertyChangeListener method should not have been called.");
            }
        );

        MethodInvocation methodInvocation = new FooMethodInvocation (null, "setFi");

        aspect.invoke(methodInvocation);
    }

    public void testInvokeWithAnnotationMissingOnParameter () throws Throwable {

        foo.addPropertyChangeListener(
            event -> {
                flag.set(true);
            }
        );

        MethodInvocation methodInvocation = new FooMethodInvocation (foo, "setFum");

        aspect.invoke(methodInvocation);

        // If the setter method exists but is not annotated then we expect the set operation to still move forward but
        // no propertyChangeEvents should be fired.
        assertFalse (flag.get());
    }

    @Test
    public void testInvokeWithSeveralParameters () throws Throwable {

        foo.addPropertyChangeListener(
            event -> {
                flag.set(true);
            }
        );

        MethodInvocation methodInvocation = new FooMethodInvocation (foo, "setFez");

        aspect.invoke(methodInvocation);

        // If the setter method exists but has more than one parameter, then we expect the set operation to still move
        // forward and we will ignore any parameters that have the Changeable annotation applied (may want to throw an
        // exception in this instance).
        assertFalse (flag.get());
    }

    public static class Bar {

        private String baz = null;

        public String getBaz() {
            return baz;
        }

        public static final String BAZ = "baz";

        public void setBaz(@Changeable(BAZ) String baz) {
            this.baz = baz;
        }
    }

    public static class Foo extends SerializableBean {

        private BigDecimal fi;

        private int fo;

        private boolean fum;

        public BigDecimal getFi() {
            return fi;
        }

        public static final String FI = "fi";

        public void setFi(@Changeable(FI) BigDecimal fi) {
            this.fi = fi;
        }

        public int getFo() {
            return fo;
        }

        public static final String FO = "fo";

        public void setFo(@Changeable(Changeable.BLANK) int fo) {
            this.fo = fo;
        }

        public boolean isFum() {
            return fum;
        }

        public static final String FUM = "fum";

        public void setFum(boolean fum) {
            this.fum = fum;
        }

        public static final String FEZ = "fez";

        public void setFez(int idx, @Changeable(FEZ) BigDecimal blah) {
        }
    }

    static class FooMethodInvocation implements MethodInvocation {

        private final Foo foo;

        private final String method;

        public FooMethodInvocation(Foo foo, String method) {
            this.foo = foo;
            this.method = method;
        }

        @Override
        public Object proceed() throws Throwable {

            foo.setFi(BigDecimal.TEN);

            return null;
        }
        
        @Override
        public Object getThis() {
            return this.foo;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return Mockito.mock(AccessibleObject.class);
        }

        @Override
        public Object[] getArguments() {
            return new String [] {};
        }

        Method getFiMethod () {

            Method result = null;

            try {
                result = Foo.class.getMethod(method, BigDecimal.class);
            } catch (Exception exception) {
                throw new RuntimeException ("The setFi method does not exist.", exception);
            }
            return result;
        }

        Method getFoMethod () {

            Method result = null;

            try {
                result = Foo.class.getMethod("setFo", Integer.class);
            } catch (Exception exception) {
                exception.printStackTrace(System.err);
                fail ("The setFo method does not exist.");
            }
            return result;
        }

        Method getFumMethod () {

            Method result = null;

            try {
                result = Foo.class.getMethod("setFum", boolean.class);
            } catch (Exception exception) {
                exception.printStackTrace(System.err);
                fail ("The setFum method does not exist.");
            }
            return result;
        }

        Method getFezMethod () {

            Method result = null;

            try {
                result = Foo.class.getMethod("setFez", int.class, BigDecimal.class);
            } catch (Exception exception) {
                exception.printStackTrace(System.err);
                fail ("The setFum method does not exist.");
            }
            return result;
        }

        @Override
        public Method getMethod() {

            Method result = null;

            if ("setFi".equals (method))
                result = getFiMethod();
            else if ("setFo".equals (method))
                result = getFoMethod();
            else if ("setFum".equals (method))
                result = getFumMethod();
            else if ("setFez".equals (method))
                result = getFezMethod();
            else
                fail ("No method named " + method + " is available on the object " + foo + " (double-check the " +
                    "parameter type if the method exists.).");

            return result;
        }
    };

    static class BarMethodInvocation implements MethodInvocation {

        private final Bar bar;

        public BarMethodInvocation(Bar bar) {
            this.bar = bar;
        }

        @Override
        public Object proceed() throws Throwable {

            bar.setBaz("Oh no!");

            return null;
        }
        
        @Override
        public Object getThis() {
            return bar;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return Mockito.mock(AccessibleObject.class);
        }

        @Override
        public Object[] getArguments() {
            return new String [] {};
        }

        @Override
        public Method getMethod() {

            Method result = null;

            try {
                result = Bar.class.getMethod("setBaz", String.class);
            } catch (Exception exception) {
                exception.printStackTrace(System.err);
                fail ("The fi method does not exist.");
            }

            return result;
        }
    };
}
