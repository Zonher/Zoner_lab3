package ru.nikita.lab2.dao.repository.impl;

import ru.nikita.lab2.dao.entity.UserEntity;
import ru.nikita.lab2.dao.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {
    @Override
    public UserEntity upsertUser(UserEntity user) {
        return doWithinTransaction(em -> em.merge(user));
    }

    @Override
    public void deleteUser(UUID userId) {
        doWithinTransaction(em -> {
            var entity = em.find(UserEntity.class, userId);
            if (entity == null) {
                throw new IllegalArgumentException(
                        "User not found: " + userId
                );
            }

            em.remove(entity);
            return null;
        });
    }

    @Override
    public Optional<UserEntity> findUserById(UUID userId) {
        var em = getEntityManager();
        return Optional.ofNullable(em.find(UserEntity.class, userId));
    }
}
