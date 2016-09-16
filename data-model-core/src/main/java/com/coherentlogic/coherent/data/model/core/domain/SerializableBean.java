package com.coherentlogic.coherent.data.model.core.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.coherentlogic.coherent.data.model.core.exceptions.CloneFailedException;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A class which all domain classes inherit from.
 *
 * @todo Consider removing the guard around calls to fire methods as the various ChangeSupport references should not
 *  really ever be null.
 *
 * @todo Can we utilize the ExceptionListener here?
 *
 * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/beans/
 *  PropertyChangeSupport.html">PropertyChangeSupport</a>
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity()
@Table(name=SerializableBean.SERIALIZABLE_BEAN)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SerializableBean<T> implements Serializable, Cloneable {

    private static final long serialVersionUID = 3557664417023869095L;

    public static final String SERIALIZABLE_BEAN = "serializable_bean",
        PRIMARY_KEY = "primaryKey", CREATED_TIME_MILLIS = "createdTimeMillis";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryKey = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedDate", nullable = false)
    private Date updatedDate;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    /**
     * @see {@link java.beans.VetoableChangeSupport}
     */
    @Transient
    @XStreamOmitField
    private transient final VetoableChangeSupport vetoableChangeSupport;

    /**
     * Note that this property must be set to a <b>non-null value</b> otherwise
     * attempts to register an instance of {@link PropertyChangeListener} with
     * an instance of this class will fail, as well as any attempt to invoke the
     * {@link java.beans.PropertyChangeSupport#firePropertyChange(
     *  java.beans.PropertyChangeEvent)} method (and/or possibly other methods).
     *
     * Note that the {@link #propertyChangeSupport} property is not final. Since
     * we're using XStream and this class implements {@link
     * java.io.Serializable} then we need to either supply a readObject method
     * or the {@link #propertyChangeSupport} property will be null when XStream
     * unmarshalls the XML. We don't want to set this property to final,
     * however, as the user may want to use a different default value than the
     * one we supply.
     *
     * @see {@link java.beans.PropertyChangeSupport}
     */
    @Transient
    @XStreamOmitField
    public transient final PropertyChangeSupport propertyChangeSupport;

    @Transient
    @XStreamOmitField
    public transient final List<AggregatePropertyChangeListener<T>> aggregatePropertyChangeListeners;

    static PropertyChangeSupport createDefaultPropertyChangeSupport (SerializableBean<?> serializableBean) {
        return new PropertyChangeSupport(serializableBean);
    }

    public SerializableBean() {
        propertyChangeSupport = createDefaultPropertyChangeSupport (this);
        vetoableChangeSupport = new VetoableChangeSupport (this);
        this.aggregatePropertyChangeListeners = new ArrayList<AggregatePropertyChangeListener<T>> ();
    }

    public SerializableBean(
        PropertyChangeSupport propertyChangeSupport,
        VetoableChangeSupport vetoableChangeSupport,
        List<AggregatePropertyChangeListener<T>> aggregatePropertyChangeListeners
    ) {
        super();
        this.propertyChangeSupport = propertyChangeSupport;
        this.vetoableChangeSupport = vetoableChangeSupport;
        this.aggregatePropertyChangeListeners = aggregatePropertyChangeListeners;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    public Long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Long primaryKey) {

        Long oldValue = this.primaryKey;

        this.primaryKey = primaryKey;

        firePropertyChange(PRIMARY_KEY, oldValue, primaryKey);
    }

    /**
     * Method returns a copy of the date parameter or null if the reference to
     * the date is null.
     *
     * @param date The date to be cloned.
     */
    protected static Date clone (Date date) {

        Date newDate = null;

        if (date != null)
            newDate = (Date) date.clone();

        return newDate;
    }

    public <X> X clone (Class<X> type) {

        Object object = clone ();

        X result = type.cast(object);

        return result;
    }

    @Override
    public Object clone () {
        try {
            return BeanUtils.cloneBean(this);
        } catch (IllegalAccessException illegalAccessException) {
            throw new CloneFailedException("Illegal access exception thrown", illegalAccessException);
        } catch (InstantiationException instantiationException) {
            throw new CloneFailedException("Failed to instantiate the class.", instantiationException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new CloneFailedException("Invocation target exception thrown.", invocationTargetException);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new CloneFailedException("No such method exception thrown.", noSuchMethodException);
        }
    }

    /**
     * Getter method for the {@link VetoableChangeSupport} instance.
     */
    @Transient
    public VetoableChangeSupport getVetoableChangeSupport() {
        // Why is this marked as transient?
        // http://stackoverflow.com/questions/3022958/
        // java-hibernate-annotations-how-to-add-methods-to-pojo-object
        return vetoableChangeSupport;
    }

    /**
     * Method adds the specified listener to the {@link #vetoableChangeSupport}
     * instance.
     */
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    /**
     * Method removes the specified listener to the {@link
     * #vetoableChangeSupport} instance.
     */
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    /**
     * Getter method for the {@link #propertyChangeSupport} property.
     */
    @Transient
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    /**
     * Method adds the specified listener to the {@link #propertyChangeSupport} instance.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Method removes the specified listener from the {@link #propertyChangeSupport} instance.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Method determines if the firePropertyChange method should be called.
     *
     * The documentation for the PropertyChangeSupport class states that "[n]o event is fired if old and new values are
     * equal and non-null." and this is what this method is checking for.
     *
     * @see https://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html
     * @see https://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html
     */
    static boolean propertiesDiffer (Object oldValue, Object newValue) {

        boolean result = false;

        if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
            result = true;

        return result;
    }

    protected boolean propertiesDiffer (int oldValue, int newValue) {

        boolean result = false;

        if (oldValue != newValue)
            result = true;

        return result;
    }

    protected boolean propertiesDiffer (boolean oldValue, boolean newValue) {

        boolean result = false;

        if (oldValue != newValue)
            result = true;

        return result;
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#firePropertyChange(
     * String, Object, Object)} method.
     */
    protected SerializableBean<T> firePropertyChange (
        String propertyName,
        Object oldValue,
        Object newValue
    ) {
        return firePropertyChange (new PropertyChangeEvent (this, propertyName, oldValue, newValue));
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#firePropertyChange(
     * String, boolean, boolean)} method.
     */
    protected SerializableBean<T> firePropertyChange (
        String propertyName,
        boolean oldValue,
        boolean newValue
    ) {
        return firePropertyChange (new PropertyChangeEvent (this, propertyName, oldValue, newValue));
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#firePropertyChange(String, int,
     * int)} method.
     */
    protected SerializableBean<T> firePropertyChange (
        String propertyName,
        int oldValue,
        int newValue
    ) {
        return firePropertyChange (new PropertyChangeEvent (this, propertyName, oldValue, newValue));
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#firePropertyChange(
     * PropertyChangeEvent)}
     * method.
     */
    protected SerializableBean<T> firePropertyChange (PropertyChangeEvent propertyChangeEvent) {

        if (propertiesDiffer(propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue()))
            propertyChangeSupport.firePropertyChange(propertyChangeEvent);

        return this;
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#fireIndexedPropertyChange(String,
     * int, Object, Object)} method.
     */
    protected SerializableBean<T> fireIndexedPropertyChange (
        String propertyName,
        int index,
        Object oldValue,
        Object newValue
    ) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);

        return this;
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#fireIndexedPropertyChange(String,
     * int, int, int)} method.
     */
    protected SerializableBean<T> fireIndexedPropertyChange (
        String propertyName,
        int index,
        int oldValue,
        int newValue
    ) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);

        return this;
    }

    /**
     * Method delegates to the
     * {@link java.beans.PropertyChangeSupport#fireIndexedPropertyChange(String,
     * int, boolean, boolean)} method.
     */
    protected SerializableBean<T> fireIndexedPropertyChange (
        String propertyName,
        int index,
        boolean oldValue,
        boolean newValue
    ) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);

        return this;
    }

    /**
     * Delegates to {@link VetoableChangeSupport#fireVetoableChange(String, Object, Object)}.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/beans/VetoableChangeSupport.html">VetoableChangeSupport</a>
     */
    protected SerializableBean<T> fireVetoableChange (String propertyName, Object oldValue, Object newValue)
        throws PropertyVetoException {

        vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);

        return this;
    }

    /**
     * Delegates to {@link VetoableChangeSupport#fireVetoableChange(String, boolean, boolean)}.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/beans/VetoableChangeSupport.html">VetoableChangeSupport</a>
     */
    protected SerializableBean<T> fireVetoableChange (String propertyName, boolean oldValue, boolean newValue)
        throws PropertyVetoException {

        vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);

        return this;
    }

    /**
     * Delegates to {@link VetoableChangeSupport#fireVetoableChange(String, int, int)}.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/beans/VetoableChangeSupport.html">VetoableChangeSupport</a>
     */
    protected SerializableBean<T> fireVetoableChange (String propertyName, int oldValue, int newValue)
        throws PropertyVetoException {

        vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);

        return this;
    }

    /**
     * Delegates to {@link VetoableChangeSupport#fireVetoableChange(String, int, int)}.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/beans/VetoableChangeSupport.html">VetoableChangeSupport</a>
     */
    protected SerializableBean<T> fireVetoableChange (PropertyChangeEvent propertyChangeEvent)
        throws PropertyVetoException {

        vetoableChangeSupport.fireVetoableChange(propertyChangeEvent);

        return this;
    }

    @Transient
    public List<AggregatePropertyChangeListener<T>> getAggregatePropertyChangeListeners() {
        return aggregatePropertyChangeListeners;
    }

    public void addAggregatePropertyChangeListener (
        AggregatePropertyChangeListener<T> aggregatePropertyChangeListener) {
        aggregatePropertyChangeListeners.add(aggregatePropertyChangeListener);
    }

    public boolean removeAggregatePropertyChangeListener (
        AggregatePropertyChangeListener<T> aggregatePropertyChangeListener) {
        return aggregatePropertyChangeListeners.remove(aggregatePropertyChangeListener);
    }

    /**
     * Method invokes the {@link AggregatePropertyChangeListener#onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent)}
     * method for all {@link AggregatePropertyChangeListener}s that have registered for update notifications.
     */
    public SerializableBean<T> fireAggregatePropertyChangeEvent (
        AggregatePropertyChangeEvent<T> aggregatePropertyChangeEvent) {

        for (AggregatePropertyChangeListener<T> aggregatePropertyChangeListener : aggregatePropertyChangeListeners)
            aggregatePropertyChangeListener.onAggregatePropertyChangeEvent(aggregatePropertyChangeEvent);

        return this;
    }

    /**
     * Returns the result of the call to EqualsBuilder#reflectionEquals(this, target, CREATED_TIME_MILLIS);
     *
     * @see {@link java.lang.Object#equals(Object)}
     */
    @Override
    public boolean equals(Object target) {
        return EqualsBuilder.reflectionEquals(this, target, CREATED_TIME_MILLIS);
    }

    /**
     * Returns the result of the call to HashCodeBuilder#reflectionHashCode(this, false);
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
