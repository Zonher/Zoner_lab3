package ru.nikita.lab2.dao.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.NumericJdbcType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Access(AccessType.FIELD)
@NamedQuery(name = "findAccountsByUser", query = "select a from AccountEntity a where a.user = :user")
public class AccountEntity {
    @Id
    private UUID id;
    @Column(name = "balance", nullable = false)
    @JdbcType(value = NumericJdbcType.class)
    private double balance;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;
    @OneToMany(
            mappedBy = "account",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<OperationEntity> operations;


    protected AccountEntity() {
        // for jpa only
    }

    public AccountEntity(double balance, UserEntity user) {
        this.id = UUID.randomUUID();
        this.balance = balance;
        this.user = user;
        this.operations = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addOperation(OperationEntity operation){
        operations.add(operation);
    }

    /**
     * @return immutable array of operations
     */
    public List<OperationEntity> getOperations() {
        return List.copyOf(operations);
    }

    public void setOperations(List<OperationEntity> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        var account = (AccountEntity) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
