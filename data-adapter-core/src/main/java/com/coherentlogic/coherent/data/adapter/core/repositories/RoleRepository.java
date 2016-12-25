package com.coherentlogic.coherent.data.adapter.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.domain.Role;

/**
 * Foundation data access class for the {@link Role} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

//    public static final String BEAN_NAME = "roleDAO";
//
//    @Override
//    public Role find(long primaryKey) {
//        return find(Role.class, primaryKey);
//    }
}
