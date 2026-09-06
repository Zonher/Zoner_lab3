package ru.nikita.lab2.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Audited;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.NumericJdbcType;
import ru.nikita.lab2.api.enumeration.OpType;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "operations")
@Access(AccessType.FIELD)
@Audited
@Audited.Table(name = "operations_aud")
public class OperationEntity {
    @Id
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private AccountEntity account;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "operation_type")
    private OpType opType;
    @Column(name = "amount", nullable = false)
    @JdbcType(value = NumericJdbcType.class)
    private double amount;
    @Column(name = "operation_at", updatable = false, nullable = false)
    private Instant oparationInstant;
    @Column(name = "commission", nullable = false)
    @JdbcType(value = NumericJdbcType.class)
    private double commission;

    protected OperationEntity() {
        // for jpa only
    }

    public OperationEntity(
            AccountEntity account,
            OpType opType,
            double amount,
            double commission
    ) {
        this.id = UUID.randomUUID();
        this.account = account;
        this.opType = opType;
        this.amount = amount;
        this.commission = commission;
        this.oparationInstant = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public double getCommission() {
        return commission;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public OpType getOpType() {
        return opType;
    }

    public double getAmount() {
        return amount;
    }

    public Instant getOparationInstant() {
        return oparationInstant;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        var operation = (OperationEntity) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
