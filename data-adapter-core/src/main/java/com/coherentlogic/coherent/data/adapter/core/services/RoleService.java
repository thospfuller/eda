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

import com.coherentlogic.coherent.data.adapter.core.repositories.RoleRepository;
import com.coherentlogic.coherent.data.model.core.domain.Role;

@Repository(RoleService.BEAN_NAME)
@Transactional
public class RoleService {

    public static final String BEAN_NAME = "roleService";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public long count() {
        return roleRepository.count();
    }

    public <S extends Role> long count(Example<S> arg0) {
        return roleRepository.count(arg0);
    }

    public void delete(Long arg0) {
        roleRepository.delete(arg0);
    }

    public void delete(Role arg0) {
        roleRepository.delete(arg0);
    }

    public void delete(Iterable<? extends Role> arg0) {
        roleRepository.delete(arg0);
    }

    public void deleteAll() {
        roleRepository.deleteAll();
    }

    public void deleteAllInBatch() {
        roleRepository.deleteAllInBatch();
    }

    public void deleteInBatch(Iterable<Role> arg0) {
        roleRepository.deleteInBatch(arg0);
    }

    public boolean exists(Long arg0) {
        return roleRepository.exists(arg0);
    }

    public <S extends Role> boolean exists(Example<S> arg0) {
        return roleRepository.exists(arg0);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findAll(Sort arg0) {
        return roleRepository.findAll(arg0);
    }

    public List<Role> findAll(Iterable<Long> arg0) {
        return roleRepository.findAll(arg0);
    }

    public <S extends Role> List<S> findAll(Example<S> arg0) {
        return roleRepository.findAll(arg0);
    }

    public <S extends Role> List<S> findAll(Example<S> arg0, Sort arg1) {
        return roleRepository.findAll(arg0, arg1);
    }

    public Page<Role> findAll(Pageable arg0) {
        return roleRepository.findAll(arg0);
    }

    public <S extends Role> Page<S> findAll(Example<S> arg0, Pageable arg1) {
        return roleRepository.findAll(arg0, arg1);
    }

    public Role findOne(Long arg0) {
        return roleRepository.findOne(arg0);
    }

    public <S extends Role> S findOne(Example<S> arg0) {
        return roleRepository.findOne(arg0);
    }

    public void flush() {
        roleRepository.flush();
    }

    public Role getOne(Long arg0) {
        return roleRepository.getOne(arg0);
    }

    public <S extends Role> List<S> save(Iterable<S> arg0) {
        return roleRepository.save(arg0);
    }

    public <S extends Role> S save(S arg0) {
        return roleRepository.save(arg0);
    }

    public <S extends Role> S saveAndFlush(S arg0) {
        return roleRepository.saveAndFlush(arg0);
    }
}
