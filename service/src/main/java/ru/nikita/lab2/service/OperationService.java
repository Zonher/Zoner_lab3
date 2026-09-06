package ru.nikita.lab2.service;

import ru.nikita.lab2.api.dto.AccountDto;
import ru.nikita.lab2.api.dto.OperationDto;
import ru.nikita.lab2.api.enumeration.OpType;
import ru.nikita.lab2.domain.Operation;

import java.util.List;
import java.util.UUID;

public interface OperationService {
    void deposit(UUID accountId, double amount);
    void withdraw(UUID accountID, double amount);
    void transfer(UUID fromAccountId, UUID toAccountId, double amount);
    List<Operation> getOperation(OpType type, UUID accountId);

}
