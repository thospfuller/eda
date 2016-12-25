package com.coherentlogic.coherent.data.adapter.core.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.adapter.core.repositories.PrivilegeRepository;
import com.coherentlogic.coherent.data.model.core.domain.Privilege;

/**
 * Foundation data access class for the {@link Privilege} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(PrivilegeService.BEAN_NAME)
@Transactional
public class PrivilegeService {

    public static final String BEAN_NAME = "privilegeService";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public PrivilegeRepository getPrivilegeRepository() {
        return privilegeRepository;
    }

    void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public long count() {
        return privilegeRepository.count();
    }

    public <S extends Privilege> long count(Example<S> arg0) {
        return privilegeRepository.count(arg0);
    }

    public void delete(Long arg0) {
        privilegeRepository.delete(arg0);
    }

    public void delete(Privilege arg0) {
        privilegeRepository.delete(arg0);
    }

    public void delete(Iterable<? extends Privilege> arg0) {
        privilegeRepository.delete(arg0);
    }

    public void deleteAll() {
        privilegeRepository.deleteAll();
    }

    public void deleteAllInBatch() {
        privilegeRepository.deleteAllInBatch();
    }

    public void deleteInBatch(Iterable<Privilege> arg0) {
        privilegeRepository.deleteInBatch(arg0);
    }

    public boolean exists(Long arg0) {
        return privilegeRepository.exists(arg0);
    }

    public <S extends Privilege> boolean exists(Example<S> arg0) {
        return privilegeRepository.exists(arg0);
    }

    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    public List<Privilege> findAll(Sort arg0) {
        return privilegeRepository.findAll(arg0);
    }

    public List<Privilege> findAll(Iterable<Long> arg0) {
        return privilegeRepository.findAll(arg0);
    }

    public <S extends Privilege> List<S> findAll(Example<S> arg0) {
        return privilegeRepository.findAll(arg0);
    }

    public <S extends Privilege> List<S> findAll(Example<S> arg0, Sort arg1) {
        return privilegeRepository.findAll(arg0, arg1);
    }

    public Page<Privilege> findAll(Pageable arg0) {
        return privilegeRepository.findAll(arg0);
    }

    public <S extends Privilege> Page<S> findAll(Example<S> arg0, Pageable arg1) {
        return privilegeRepository.findAll(arg0, arg1);
    }

    public Privilege findOne(Long arg0) {
        return privilegeRepository.findOne(arg0);
    }

    public <S extends Privilege> S findOne(Example<S> arg0) {
        return privilegeRepository.findOne(arg0);
    }

    public void flush() {
        privilegeRepository.flush();
    }

    public Privilege getOne(Long arg0) {
        return privilegeRepository.getOne(arg0);
    }

    public <S extends Privilege> List<S> save(Iterable<S> arg0) {
        return privilegeRepository.save(arg0);
    }

    public <S extends Privilege> S save(S arg0) {
        return privilegeRepository.save(arg0);
    }

    public <S extends Privilege> S saveAndFlush(S arg0) {
        return privilegeRepository.saveAndFlush(arg0);
    }
}
