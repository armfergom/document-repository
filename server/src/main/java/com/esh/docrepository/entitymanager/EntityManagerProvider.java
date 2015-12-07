package com.esh.docrepository.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Entity manager provider that implements the singleton pattern to return the EntityManager
 * 
 * @author armfergom
 *
 */
public class EntityManagerProvider {

    private static final String PERSISTENCE_UNIT_ID = "doc-repository";

    // The JPA entity manager used to interact with the database
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_ID).createEntityManager();
        }
        return entityManager;
    }
}
