package com.coherentlogic.coherent.data.adapter.core.db.integration.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;

/**
 * A base class for building data access objects that provides access to the {@link EntityManager} along with some basic
 * create, read, update, and delete (CRUD) methods.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Deprecated
@Transactional
public interface SerializableBeanRepository<T extends SerializableBean<?>> extends JpaRepository<T, Long> {

//    private static final Logger log = LoggerFactory.getLogger(SerializableBeanRepository.class);
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private Class<T> targetClass;
//
//    public SerializableBeanRepository() {
//        super();
//    }
//
//    public SerializableBeanRepository(EntityManager entityManager) {
//        super();
//        this.entityManager = entityManager;
//    }
//
//    /**
//     * Getter method for the {@link EntityManager}.
//     *
//     * @return A reference to the {@link EntityManager} assigned to this
//     *  instance of {@link SerializableBeanRepository}.
//     */
//    protected EntityManager getEntityManager() {
//        return entityManager;
//    }
//
//    /**
//     * Method delegates to the {@link EntityManager#persist(Object)} method.
//     *
//     * @param target The target class to merge (update).
//     */
//    public void persist (T target) {
//        log.debug("persist: method begins; target: " + target);
//
//        entityManager.persist(target);
//    }
//
//    /**
//     * Method delegates to the {@link EntityManager#merge(Object)} method.
//     *
//     * @param target The target class to merge (update).
//     *
//     * @return The merged instance of T.
//     */
//    public T merge (T target) {
//        log.debug("merge: method begins; target: " + target);
//
//        return entityManager.merge(target);
//    }
//
//    /**
//     * Method delegates to the {@link EntityManager#find(Class, Object)} method.
//     *
//     * @param targetClass The type of class to be returned.
//     * @param primaryKey The primary key value.
//     *
//     * @return An instance of the targetClass or null if none exists.
//     */
//    public T find (
//        Class<T> targetClass,
//        Long primaryKey
//    ) {
//        log.debug("find: method begins; primaryKey: " + primaryKey);
//
//        T result = entityManager.find(targetClass, primaryKey);
//
//        return result;
//    }
//
//    /**
//     * Method delegates to the {@link EntityManager#remove(Object)} method.
//     *
//     * @param target The object being removed.
//     */
//    public void remove (T target) {
//        log.debug("remove: method begins; target: " + target);
//
//        entityManager.remove(target);
//    }
//
//    /**
//     * Method signature for finding the domain object of type T with the
//     * specified primaryKey.
//     *
//     * @param primaryKey The primary key.
//     *
//     * @return An instance of the domain object of type T.
//     */
//    public abstract T find (long primaryKey);
//
//	@Override
//	public <S extends T> long count(Example<S> example) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public <S extends T> boolean exists(Example<S> example) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
//		
//		
//	}
//
//	@Override
//	public <S extends T> S findOne(Example<S> example) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Page<T> findAll(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long count() {
//		return 0;
//	}
//
//	@Override
//	public void delete(Iterable<? extends T> iterable) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(Long primaryKey) {
//		
//	}
//
//	@Override
//	public void delete(T target) {
//	}
//
//	@Override
//	public void deleteAll() {
//	}
//
//	@Override
//	public boolean exists(Long primaryKey) {
//		return false;
//	}
//
//	@Override
//	public T findOne(Long primaryKey) {
//		return null;
//	}
//
//	@Override
//	public <S extends T> S save(S target) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void deleteAllInBatch() {
//		
//		
//		
//	}
//
//	@Override
//	public void deleteInBatch(Iterable<T> iterable) {
//	}
//
//	@Override
//	public List<T> findAll() {
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//		CriteriaQuery<T> query = (CriteriaQuery<T>) criteriaBuilder.createQuery(targetClass);
//
//		Root<T> rootEntry = query.from(targetClass);
//
//        CriteriaQuery<T> all = (CriteriaQuery<T>) query.select(rootEntry);
//
//        TypedQuery<T> allQuery = entityManager.createQuery(all);
//
//        return allQuery.getResultList();
//	}
//
//	@Override
//	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
//		return null;
//	}
//
//	@Override
//	public <S extends T> List<S> findAll(Example<S> example) {
//		return null;
//	}
//
//	@Override
//	public List<T> findAll(Iterable<Long> iterable) {
//		return null;
//	}
//
//	@Override
//	public List<T> findAll(Sort sort) {
//		return null;
//	}
//
//	@Override
//	public void flush() {
//		
//	}
//
//	@Override
//	public T getOne(Long primaryKey) {
//		return null;
//	}
//
//	@Override
//	public <S extends T> List<S> save(Iterable<S> iterable) {
//		return null;
//	}
//
//	@Override
//	public <S extends T> S saveAndFlush(S target) {
//		return null;
//	}
}
