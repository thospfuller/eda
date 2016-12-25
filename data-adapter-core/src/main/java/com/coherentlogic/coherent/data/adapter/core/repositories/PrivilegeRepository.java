package com.coherentlogic.coherent.data.adapter.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.domain.Privilege;

@Transactional
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
