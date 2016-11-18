package com.coherentlogic.coherent.data.adapter.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.adapter.core.db.integration.dao.SerializableDAO;
import com.coherentlogic.coherent.data.model.core.domain.Privilege;

/**
 * Foundation data access class for the {@link Privilege} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(PrivilegeDAO.BEAN_NAME)
@Transactional
public class PrivilegeDAO extends SerializableDAO<Privilege> {

    public static final String BEAN_NAME = "privilegeDAO";

    @Override
    public Privilege find(long primaryKey) {
        return find(Privilege.class, primaryKey);
    }
}
