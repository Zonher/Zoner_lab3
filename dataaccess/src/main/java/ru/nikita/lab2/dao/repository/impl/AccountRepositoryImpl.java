package ru.nikita.lab2.dao.repository.impl;

import ru.nikita.lab2.dao.entity.AccountEntity;
import ru.nikita.lab2.dao.entity.UserEntity;
import ru.nikita.lab2.dao.repository.AccountRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountRepositoryImpl extends BaseRepository implements AccountRepository {
    @Override
    public AccountEntity upsertAccount(AccountEntity account) {
        return doWithinTransaction(em -> em.merge(account));
    }

    @Override
    public void deleteAccount(AccountEntity account) {
        doWithinTransaction(em -> {
            em.remove(account);
            return null;
        });
    }

    @Override
    public Set<AccountEntity> findAccountsByUser(UserEntity user) {
        var em = getEntityManager();
        return em.createNamedQuery("findAccountsByUser", AccountEntity.class)
                .setParameter("user", user)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public AccountEntity findAccountById(UUID id) {
        var em = getEntityManager();
        return em.find(AccountEntity.class, id);
    }
}
