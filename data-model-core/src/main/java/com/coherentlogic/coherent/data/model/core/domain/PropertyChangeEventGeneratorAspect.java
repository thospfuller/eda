package com.coherentlogic.coherent.data.model.core.domain;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.exceptions.MisconfiguredException;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;

/**
 * <bean id="marketPriceSetterInterceptor"
 *  class="com.coherentlogic.coherent.data.model.core.domain.PropertyChangeEventGeneratorAspect">
 *     <constructor-arg value="com.coherentlogic.coherent.datafeed.domain.MarketPrice"/>
 * </bean>
 *
 * <aop:config>
 *     <aop:pointcut id="marketPriceSetterPointcut"
 *      expression="execution(public void com.coherentlogic.coherent.datafeed.domain.MarketPrice.set*(..))"/>
 *     <aop:advisor pointcut-ref="marketPriceSetterPointcut"
 *      advice-ref="marketPriceSetterInterceptor" />
 * </aop:config>
 *
 * public static final String BID = "bid";
 *
 * @RFAType(type=MarketPriceConstants.BID)
 * @Adapt(using=OMMNumericAdapter.class)
 * public void setBid(@Changeable(BID) BigDecimal bid) {
 *     this.bid = bid;
 * }
 *
 * @param <T> The class type which is being intercepted.
 *
 * @see {@link com.coherentlogic.coherent.datafeed.annotations#Changeable}
 */
@Aspect
public class PropertyChangeEventGeneratorAspect<T> implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(PropertyChangeEventGeneratorAspect.class);

    static final String FIRE_PROPERTY_CHANGE = "firePropertyChange";

    private final Class<T> targetClass;

    private final Method firePropertyChangeMethod;

    public PropertyChangeEventGeneratorAspect(Class<T> targetClass) {

        this.targetClass = targetClass;

        try {
            firePropertyChangeMethod = SerializableBean.class.getDeclaredMethod(
                FIRE_PROPERTY_CHANGE, PropertyChangeEvent.class);
        } catch (Exception e) {
            throw new RuntimeException ("An exception was thrown when attempting to get the method named " +
                FIRE_PROPERTY_CHANGE + ".", e);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object targetObject = invocation.getThis();

        SerializableBean target = null;

        if (targetObject != null && targetObject instanceof SerializableBean)
            target = (SerializableBean) targetObject;
        else if (targetObject == null)
            throw new NullPointerException("The targetObject is null.");
        else
            throw new MisconfiguredException("The bean must extend SerializableBean; targetObject class: " +
                targetObject.getClass());

        Method method = invocation.getMethod();

        int parameterCount = method.getParameterCount();

        Object result = null;

        if (parameterCount == 1) {

            Parameter[] parameters = method.getParameters();

            Parameter parameter = parameters[0];

            if (parameter.isAnnotationPresent(Changeable.class)) {

                Changeable changeable = parameter.getAnnotation(Changeable.class);

                String parameterName = changeable.value();

                if (Changeable.BLANK.equals(parameterName))
                    throw new MisconfiguredException ("The changeable.value (parameterName) cannot be blank.");

                Field field = targetClass.getDeclaredField(parameterName);

                log.debug("parameterName: " + parameterName + ", field: " + field.getName());

                field.setAccessible(true);

                Object oldValue = field.get(targetObject);

                result = invocation.proceed();

                Object newValue = field.get(targetObject);

                log.debug("oldValue: " + oldValue + ", newValue: " + newValue);

                field.setAccessible(false);

                PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(
                    target,
                    parameterName,
                    oldValue,
                    newValue
                );

                firePropertyChangeMethod.invoke(target, propertyChangeEvent);

            } else {
                log.debug("The method " + method + " exists and has one parameter however the parameter has not " +
                    "been annotated with the " + Changeable.class + " annotation, hence no PropertyChangeEvents will " +
                    "be fired.");

                result = invocation.proceed();
            }
        } else {
            result = invocation.proceed();
        }
        return result;
    }
}
