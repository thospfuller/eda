package com.coherentlogic.coherent.data.model.core.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * A representation of a role for projects using Spring Security.
 *
 * @see {@link User}
 * @see {@link Privilege}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class Role extends SerializableBean<User> {

    private static final long serialVersionUID = -8984788924094495235L;

    private static final String
        NAME = "name",
        ROLE = "role",
        ROLE_PRIVILEGES = "role_privileges",
        PRIVILEGES = "privileges",
        ROLE_ID = "role_id",
        PRIVILEGE_KEY = "privilege_key",
        USERS = "users";

    @Column(name = NAME, nullable = false)
    private String name;

    @ManyToMany(mappedBy = ROLE)
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
        name = ROLE_PRIVILEGES, 
        joinColumns = @JoinColumn(name = ROLE_ID, referencedColumnName = PRIMARY_KEY), 
        inverseJoinColumns = @JoinColumn(name = PRIVILEGE_KEY, referencedColumnName = PRIMARY_KEY)
    )
    private Collection<Privilege> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String oldValue = this.name;

        this.name = name;

        firePropertyChange(NAME, oldValue, name);
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {

        Collection<User> oldValue = this.users;

        this.users = users;

        firePropertyChange(USERS, oldValue, users);
    }

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
