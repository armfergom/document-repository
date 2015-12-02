package com.esh.docrepository.rs.dao;

import java.io.Serializable;

import com.esh.docrepository.rs.entitymanager.EntityManagerProvider;

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
