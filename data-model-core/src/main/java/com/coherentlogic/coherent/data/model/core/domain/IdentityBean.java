package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.util.Constants.ID;
import static com.coherentlogic.coherent.data.model.core.util.Constants.IDENTITY_BEAN;
import static com.coherentlogic.coherent.data.model.core.util.Constants.IDENTITY_VALUE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * A bean that has an id property and associated getter and setter methods.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=IDENTITY_BEAN)
public class IdentityBean extends SerializableBean
    implements IdentitySpecification<String> {

    private static final long serialVersionUID = 6114953858586102298L;

    @XStreamAlias(ID)
    @XStreamAsAttribute
    private String id = null;

    @Column(name=IDENTITY_VALUE)
    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        IdentityBean other = (IdentityBean) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IdentityBean [id=" + id + ", toString()=" + super.toString()
            + "]";
    }
}
