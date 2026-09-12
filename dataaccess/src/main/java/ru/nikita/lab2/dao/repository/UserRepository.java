package ru.nikita.lab2.dao.repository;

import ru.nikita.lab2.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByLogin(String login);
    @EntityGraph(attributePaths = "friends")
    @Query("""
    select u
    from UserEntity u
    where u.id = :id
""")
    Optional<UserEntity> findUserById(@Param("id") UUID id);
    @Query(value = """
            select u
            from UserEntity u
            where (:hairColor is null or u.hairColor = :hairColor)
            and (:gender is null or u.gender = :gender)
            """)
    List<UserEntity> findFiltered(
            @Param("hairColor") HairColor hairColor,
            @Param("gender") Gender gender
    );
    @Query("""
    select count(f)
    from UserEntity u
    join u.friends f
    where u.id = :userId
    and f.id = :friendId
""")
    long countFriendship(
            @Param("userId") UUID userId,
            @Param("friendId") UUID friendId
    );

}
