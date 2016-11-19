package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.NAME;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.PRIVILEGES;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ROLES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A representation of a privilege for projects using Spring Security.
 *
 * @see {@link User}
 * @see {@link Role}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=SecurityConstants.PRIVILEGE)
public class Privilege extends SerializableBean<Privilege> {

    private static final long serialVersionUID = 8160450404279155148L;

    public static enum Name {
        READ_PRIVILEGE, WRITE_PRIVILEGE;
    }

    private String name;
 
    private List<Role> roles = new ArrayList<Role> ();

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String oldValue = this.name;

        this.name = name;

        firePropertyChange(NAME, oldValue, name);
    }

    public void setName(Name name) {
        setName (name.toString());
    }

    public void setNameToRead () {
        setName (Name.READ_PRIVILEGE);
    }

    public void setNameToWrite () {
        setName (Name.WRITE_PRIVILEGE);
    }

    @ManyToMany(targetEntity=Role.class, mappedBy = PRIVILEGES, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {

        List<Role> oldValue = this.roles;

        this.roles = roles;

        firePropertyChange(ROLES, oldValue, roles);
    }

    public Privilege addRoles (Role... roles) {

        for (Role role : roles)
            this.roles.add(role);

        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
        Privilege other = (Privilege) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Privilege [name=" + name + "]";
    }
}
