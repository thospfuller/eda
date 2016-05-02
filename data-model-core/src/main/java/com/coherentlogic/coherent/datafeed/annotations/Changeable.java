package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that is used to identify a setter method property, by name, that can be changed -- for example:
 * <pre>
 * public static final String BID = "bid";
 *
 * @RFAType(type=MarketPriceConstants.BID)
 * @Adapt(using=OMMNumericAdapter.class)
 * public void setBid(@Changeable(BID) BigDecimal bid) {
 *     this.bid = bid;
 * }</pre>
 *
 * In the above example it is important to note that the annotation value (BID) must match <i>exactly</i> the name of
 * the corresponding property in the class which is being modified.
 *
 * @see {@link com.coherentlogic.coherent.data.model.core.domain#PropertyChangeEventGeneratorAspect}
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Changeable {

    public static final String BLANK = "";

    String value() default "";
}
