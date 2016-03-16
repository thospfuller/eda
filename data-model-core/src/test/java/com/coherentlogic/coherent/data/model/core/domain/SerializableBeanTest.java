package com.coherentlogic.coherent.data.model.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.util.Flag;

/**
 * Unit test for the {@link SerializableBean} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SerializableBeanTest {

    private SerializableBean serializableBean = null;

    private Flag flag = null;

    @Before
    public void setUp () {
        flag = new Flag ();
        serializableBean = new SerializableBean ();

        serializableBean.addPropertyChangeListener(
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    flag.setValue(true);
                }
            }
        );
    }

    @After
    public void tearDown () {
        flag = null;
        serializableBean = null;
    }

    private Date create (Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        Date date = calendar.getTime();

        return date;
    }
    
    private Date create (int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();

        return create (calendar, year, month, day);
    }

    @Test
    public void testCloneDate () {

        Date expected = create (2000, Calendar.AUGUST, 15);

        Date actual = SerializableBean.clone(expected);

        assertEquals (expected, actual);
    }

    @Test
    public void testCloneDateWithLocalization () {
        Calendar calendar = Calendar.getInstance(
            TimeZone.getTimeZone ("UTC"), Locale.ENGLISH);

        Date expected = create (calendar, 2000, Calendar.AUGUST, 15);

        Date actual = SerializableBean.clone(expected);

        assertEquals (expected, actual);
    }

    @Test
    public void testCloneDateWithLocalization2 () {
        Calendar calendar = Calendar.getInstance(
            TimeZone.getTimeZone ("UTC"), Locale.CHINA);

        Date expected = create (calendar, 2000, Calendar.AUGUST, 15);

        Date actual = SerializableBean.clone(expected);

        assertEquals (expected, actual);
    }

    @Test
    public void testPropertyChangeEventFiredOnPrimaryKey () {

        serializableBean.setPrimaryKey(-1999L);

        assertTrue (flag.isValue());
    }

//    /**
//     * This check ensures that no NPE is thrown when the PropertyChangeSupport
//     * reference is null.
//     *
//     * @deprecated The PropertyChangeSupport and VetoableChangeSupport should never be null.
//     */
//    @Test
//    public void testNullPropertyChangeSupport () {
//
//        serializableBean = new SerializableBean (null, null);
//
//        serializableBean.fireIndexedPropertyChange(null, 1, true, false);
//        serializableBean.fireIndexedPropertyChange(null, 0, 2, 3);
//        serializableBean.fireIndexedPropertyChange(null, 1, "fee", "bar");
//        serializableBean.firePropertyChange(
//            new PropertyChangeEvent(this, "fee", "fi", "fo"));
//        serializableBean.firePropertyChange("fee", false, true);
//        serializableBean.firePropertyChange(null, 2, 3);
//        serializableBean.firePropertyChange("fee", "fi", "fo");
//    }

    @Test
    public void testPropertiesDifferForObjectObject () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", null, "bar");

        assertTrue (latch.get());
    }

    @Test
    public void testPropertiesDoNotDifferForObjectObject () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", "bar", "bar");

        assertFalse (latch.get());
    }

    @Test
    public void testPropertiesBothNullForObjectObject () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", null, null);

        assertFalse (latch.get());
    }

    @Test
    public void testPropertiesDifferForIntInt () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", 123, 456);

        assertTrue (latch.get());
    }

    @Test
    public void testPropertiesDoNotDifferForIntInt () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", 123, 123);

        assertFalse (latch.get());
    }

    @Test
    public void testPropertiesDifferForBooleanBoolean () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", false, true);

        assertTrue (latch.get());
    }

    @Test
    public void testPropertiesDoNotDifferForBooleanBoolean () {

        final AtomicBoolean latch = new AtomicBoolean (false);

        serializableBean.addPropertyChangeListener(
            listener -> {
                latch.set(true);
            }
        );

        serializableBean.firePropertyChange("foo", true, true);

        assertFalse (latch.get());
    }

    @Test
    public void testVetoableChangeSupportIsNotNull () throws PropertyVetoException {
        /*
         * Test should not result in a NPE.
         */
        serializableBean.addVetoableChangeListener(
            listener -> {
            }
        );

        serializableBean.fireVetoableChange("foo", false, true);
    }

    @Test
    public void testEquals () {
        assertTrue (serializableBean.equals(serializableBean));
    }

    @Test
    public void testNotEquals () {
        assertTrue (serializableBean.equals(serializableBean.clone()));
    }
}
