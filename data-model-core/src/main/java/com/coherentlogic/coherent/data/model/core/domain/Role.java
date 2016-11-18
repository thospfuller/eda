package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.NAME;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.PRIVILEGES;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.PRIVILEGE_KEY;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ROLES;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ROLE_ID;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ROLE_PRIVILEGES;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.USERS;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A representation of a role for projects using Spring Security.
 *
 * @see {@link User}
 * @see {@link Privilege}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=SecurityConstants.ROLE)
public class Role extends SerializableBean<Role> {

    private static final long serialVersionUID = 2650772010408194802L;

    public static enum Name {
        ROLE_USER, ROLE_ADMIN;
    }

    @Column(name = NAME, nullable = false)
    private String name;

    private Collection<User> users;

    private Collection<Privilege> privileges;

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

    public void setNameToUser() {
        setName (Name.ROLE_USER);
    }

    public void setNameToAdmin() {
        setName (Name.ROLE_ADMIN);
    }

    @ManyToMany(targetEntity=User.class, mappedBy = ROLES, fetch=FetchType.EAGER)
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {

        Collection<User> oldValue = this.users;

        this.users = users;

        firePropertyChange(USERS, oldValue, users);
    }

    @ManyToMany
    @JoinTable(
        name = ROLE_PRIVILEGES, 
        joinColumns = @JoinColumn(name = ROLE_ID, referencedColumnName = PRIMARY_KEY), 
        inverseJoinColumns = @JoinColumn(name = PRIVILEGE_KEY, referencedColumnName = PRIMARY_KEY)
    )
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {

        Collection<Privilege> oldValue = this.privileges;

        this.privileges = privileges;

        firePropertyChange(PRIVILEGES, oldValue, privileges);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((privileges == null) ? 0 : privileges.hashCode());
        result = prime * result + ((users == null) ? 0 : users.hashCode());
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
        Role other = (Role) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (privileges == null) {
            if (other.privileges != null)
                return false;
        } else if (!privileges.equals(other.privileges))
            return false;
        if (users == null) {
            if (other.users != null)
                return false;
        } else if (!users.equals(other.users))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Role [name=" + name + ", users=" + users + ", privileges=" + privileges + ", toString()="
            + super.toString() + "]";
    }
}
