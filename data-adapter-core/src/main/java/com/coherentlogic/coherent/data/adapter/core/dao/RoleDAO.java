package com.coherentlogic.coherent.data.adapter.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.adapter.core.db.integration.dao.SerializableDAO;
import com.coherentlogic.coherent.data.model.core.domain.Role;

/**
 * Foundation data access class for the {@link Role} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(RoleDAO.BEAN_NAME)
@Transactional
public class RoleDAO extends SerializableDAO<Role> {

    public static final String BEAN_NAME = "roleDAO";

    @Override
    public Role find(long primaryKey) {
        return find(Role.class, primaryKey);
    }
}
