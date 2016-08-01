package com.coherentlogic.coherent.data.model.core.domain;

/**
 * A partial implementation of a bean that has both an identity and a type-specific value.
 *
 * We do not keep an instance of type T as a data member here because we need to use XStream annotations to indicate
 * what converter to use.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The value type.
 */
public abstract class AbstractIdentityValueBean<T> extends IdentityBean
    implements IdentityValueSpecification<String, T> {

    private static final long serialVersionUID = -4809835788536406562L;

    public AbstractIdentityValueBean() {
        super();
    }

    @Override
    public abstract void setValue (T value);

    @Override
    public abstract T getValue ();
}
