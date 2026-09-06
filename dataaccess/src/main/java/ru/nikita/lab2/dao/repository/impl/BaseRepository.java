package ru.nikita.lab2.dao.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import ru.nikita.lab2.dao.utils.EntityManagerUtil;

import java.util.function.Function;

abstract class BaseRepository {

    protected <R> R doWithinTransaction(Function<EntityManager, R> action) {
        var em = getEntityManager();
        beginTransaction(em);
        R result;
        try {
            result = action.apply(em);
            commitTransaction(em);
            return result;
        } catch (PersistenceException ex) {
            rollbackTransaction(em);
            throw ex;
        }
    }

    protected void beginTransaction(EntityManager em) {
        em.getTransaction().begin();
    }

    protected void commitTransaction(EntityManager em) {
        em.getTransaction().commit();
    }

    protected void rollbackTransaction(EntityManager em) {
        em.getTransaction().rollback();
    }

    protected EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }
}
