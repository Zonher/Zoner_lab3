package ru.nikita.lab2.dao.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nikita.lab2.api.enumeration.OpType;
import ru.nikita.lab2.dao.entity.OperationEntity;

import java.util.List;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
    @EntityGraph(attributePaths = "account")
    @Query("""
        select o
        from OperationEntity o
        where (:type is null or o.opType = :type)
            and (:accountId is null or o.account.id = :accountId)
                order by o.oparationInstant desc
                    """)
    List<OperationEntity> findFiltered(
            @Param("type")OpType type,
            @Param("accountId") UUID accountId
            );
}
