package ru.nikita.lab2.dao.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class EntityManagerUtil {
    private final static String PERSISTENCE_UNIT = "Lab2PU";
    private static EntityManagerFactory emf;

    private EntityManagerUtil() {
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        synchronized (EntityManagerFactory.class) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            }
        }

        return emf;
    }

    /**
     * @return new entity manager instance
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
