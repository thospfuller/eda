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

import com.coherentlogic.coherent.data.adapter.core.repositories.UserRepository;
import com.coherentlogic.coherent.data.model.core.domain.User;

@Repository(UserService.BEAN_NAME)
@Transactional
public class UserService {

    public static final String BEAN_NAME = "userService";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long count() {
        return userRepository.count();
    }

    public <S extends User> long count(Example<S> arg0) {
        return userRepository.count(arg0);
    }

    public void delete(Long arg0) {
        userRepository.delete(arg0);
    }

    public void delete(User arg0) {
        userRepository.delete(arg0);
    }

    public void delete(Iterable<? extends User> arg0) {
        userRepository.delete(arg0);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

    public void deleteInBatch(Iterable<User> arg0) {
        userRepository.deleteInBatch(arg0);
    }

    public boolean exists(Long arg0) {
        return userRepository.exists(arg0);
    }

    public <S extends User> boolean exists(Example<S> arg0) {
        return userRepository.exists(arg0);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(Sort arg0) {
        return userRepository.findAll(arg0);
    }

    public List<User> findAll(Iterable<Long> arg0) {
        return userRepository.findAll(arg0);
    }

    public <S extends User> List<S> findAll(Example<S> arg0) {
        return userRepository.findAll(arg0);
    }

    public <S extends User> List<S> findAll(Example<S> arg0, Sort arg1) {
        return userRepository.findAll(arg0, arg1);
    }

    public Page<User> findAll(Pageable arg0) {
        return userRepository.findAll(arg0);
    }

    public <S extends User> Page<S> findAll(Example<S> arg0, Pageable arg1) {
        return userRepository.findAll(arg0, arg1);
    }

    public User findOne(Long arg0) {
        return userRepository.findOne(arg0);
    }

    public <S extends User> S findOne(Example<S> arg0) {
        return userRepository.findOne(arg0);
    }

    public void flush() {
        userRepository.flush();
    }

    public User getOne(Long arg0) {
        return userRepository.getOne(arg0);
    }

    public <S extends User> List<S> save(Iterable<S> arg0) {
        return userRepository.save(arg0);
    }

    public <S extends User> S save(S arg0) {
        return userRepository.save(arg0);
    }

    public <S extends User> S saveAndFlush(S arg0) {
        return userRepository.saveAndFlush(arg0);
    }
}
