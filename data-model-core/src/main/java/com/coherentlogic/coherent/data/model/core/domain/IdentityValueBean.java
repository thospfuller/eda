package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.util.Constants.VALUE;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * Note that children of this class currently need to have an associated converter that extends from
 * IdentityValueBeanConverter registered, otherwise you'll see null values in the result (there really needs to be a
 * better solution for this).
 *
 * @todo We need to consider making the id generic since there is at least one version of this where the id is a number
 * (see IndicatorSource). Another solution would be to add a helper method to convert this to a number however this is
 * less ideal.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class IdentityValueBean extends AbstractIdentityValueBean<String> {

    private static final long serialVersionUID = 3114464451308368426L;

    @XStreamConverter(StringConverter.class)
    private String value = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {

        String oldValue = this.value;

        this.value = value;

        firePropertyChange(VALUE, oldValue, value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdentityValueBean other = (IdentityValueBean) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IdentityValueBean [value=" + value + ", toString()=" + super.toString() + "]";
    }
}
