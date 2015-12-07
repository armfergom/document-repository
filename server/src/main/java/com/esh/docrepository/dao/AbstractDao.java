package com.esh.docrepository.dao;

import java.io.Serializable;

import com.esh.docrepository.entitymanager.EntityManagerProvider;

/**
 *  Main abstract utility class for Dao handling, it provides two basic functions: persist and find.
 */
public class AbstractDao<T> extends EntityManagerProvider {

    private Class<T> type;

    public AbstractDao(Class<T> type) {
        this.type = type;
    }

    protected void persist(T object) {
        getEntityManager().persist(object);
    }

    protected T find(Serializable id) {
        return getEntityManager().find(type, id);
    }
}
