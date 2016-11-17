package com.coherentlogic.coherent.data.model.core.domain;

import java.util.Collection;

import javax.persistence.ManyToMany;

/**
 * A representation of a privilege for projects using Spring Security.
 *
 * @see {@link User}
 * @see {@link Role}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class Privilege extends SerializableBean<Privilege> {

    private static final long serialVersionUID = -4093874560457654213L;

    public static final String PRIVILEGES = "privileges", NAME = "name", ROLES = "roles";

    private String name;
 
    @ManyToMany(mappedBy = PRIVILEGES)
    private Collection<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String oldValue = this.name;

        this.name = name;

        firePropertyChange(NAME, oldValue, name);
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {

        Collection<Role> oldValue = this.roles;

        this.roles = roles;

        firePropertyChange(ROLES, oldValue, roles);
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
        return "Privilege [name=" + name + ", roles=" + roles + ", toString()=" + super.toString() + "]";
    }
}
