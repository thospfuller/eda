package com.coherentlogic.coherent.data.adapter.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.domain.User;

/**
 * Foundation data access class for the {@link User} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}
