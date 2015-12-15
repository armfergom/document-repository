package com.esh.docrepo.rest.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Main abstract utility class for Dao handling, it provides two basic functions: persist and find.
 */
@Repository
public class AbstractDao<T> {

    private EntityManager entityManager;
    private Class<T> type;

    public AbstractDao() {
    }
    
    public AbstractDao(Class<T> type) {
        this.type = type;
    }

    @Transactional
    protected void persist(T object) {
        getEntityManager().persist(object);
    }

    @Transactional(readOnly = true)
    protected T find(Serializable id) {
        return getEntityManager().find(type, id);
    }

    ///////////////////////////////////////////////
    //////////// Entity manager methods ///////////
    ///////////////////////////////////////////////

    // Spring injecting the entity manager
    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
