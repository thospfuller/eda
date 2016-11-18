package com.coherentlogic.coherent.data.adapter.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.adapter.core.db.integration.dao.SerializableDAO;
import com.coherentlogic.coherent.data.model.core.domain.User;

/**
 * Foundation data access class for the {@link User} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(UserDAO.BEAN_NAME)
@Transactional
public class UserDAO extends SerializableDAO<User> {

    public static final String BEAN_NAME = "userDAO";

    @Override
    public User find(long primaryKey) {
        return find(User.class, primaryKey);
    }
}
