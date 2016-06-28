package com.coherentlogic.coherent.data.model.core.annotations;

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
 * ** IMPORTANT **
 *
 * In the above example it is important to note that the annotation value (BID) must match <i>exactly</i> the name of
 * the corresponding property in the class which is being modified. We also need the value since once the class has been
 * compiled, the bid parameter name may change to arg0, for example, so we can't rely on the parameter (we can set a
 * compiler option to keep this information, but then that forces us to always set that flag).
 *
 * Finally, if the property name is refactored the developer must make sure the static constant property name is also
 * updated.
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
