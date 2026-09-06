package ru.nikita.lab2.service;

import ru.nikita.lab2.domain.Account;

import java.util.List;
import java.util.UUID;

public interface AccountCRUDService {
    Account createAccount(UUID userId);

    void removeAccount(UUID accountId);

    Account getAccount(UUID accountID);

    List<Account> getAccounts();

    List<Account> getAccountsByUserId(UUID userId);
}
