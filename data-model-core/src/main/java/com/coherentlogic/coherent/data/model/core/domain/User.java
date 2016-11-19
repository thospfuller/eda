package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.EMAIL;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ENABLED;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.FIRST_NAME;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.LAST_NAME;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.PASSWORD;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.ROLE_KEY;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.TOKEN_EXPIRED;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.USERS_ROLLS;
import static com.coherentlogic.coherent.data.model.core.domain.SecurityConstants.USER_KEY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A representation of a user for projects using Spring Security.
 *
 * @see {@link Role}
 * @see {@link Privilege}
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=SecurityConstants.USER)
public class User extends SerializableBean<User> {

    private static final long serialVersionUID = 7674966586748982193L;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean enabled;

    private boolean tokenExpired;

    private List<Role> roles = new ArrayList <Role> ();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        String oldValue = this.firstName;

        this.firstName = firstName;

        firePropertyChange(FIRST_NAME, oldValue, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        String oldValue = this.lastName;

        this.lastName = lastName;

        firePropertyChange(LAST_NAME, oldValue, lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        String oldValue = this.email;

        this.email = email;

        firePropertyChange(EMAIL, oldValue, email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        String oldValue = this.password;

        this.password = password;

        firePropertyChange(PASSWORD, oldValue, password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {

        boolean oldValue = this.enabled;

        this.enabled = enabled;

        firePropertyChange(ENABLED, oldValue, enabled);
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {

        boolean oldValue = this.tokenExpired;

        this.tokenExpired = tokenExpired;

        firePropertyChange(TOKEN_EXPIRED, oldValue, tokenExpired);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( 
        name = USERS_ROLLS,
        joinColumns = @JoinColumn(name = USER_KEY, referencedColumnName = PRIMARY_KEY), 
        inverseJoinColumns = @JoinColumn(name = ROLE_KEY, referencedColumnName = PRIMARY_KEY))
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User addRoles (Role... roles) {

        for (Role role : roles)
            this.roles.add(role);

        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + (tokenExpired ? 1231 : 1237);
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (enabled != other.enabled)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (tokenExpired != other.tokenExpired)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [TBD]";
    }
}
