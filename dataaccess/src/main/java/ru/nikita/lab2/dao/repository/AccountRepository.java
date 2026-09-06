package ru.nikita.lab2.dao.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.lab2.dao.entity.AccountEntity;
import ru.nikita.lab2.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @EntityGraph(attributePaths = "user")
    List<AccountEntity> findAllUsersById(UUID id);

    @Override
    @EntityGraph(attributePaths = "user")
    List<AccountEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "user")
    Optional<AccountEntity> findById(UUID id);

}
