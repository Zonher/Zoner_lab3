package ru.nikita.lab2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.lab2.dao.entity.AccountEntity;
import ru.nikita.lab2.dao.repository.AccountRepository;
import ru.nikita.lab2.dao.repository.UserRepository;
import ru.nikita.lab2.domain.Account;
import ru.nikita.lab2.service.AccountCRUDService;
import ru.nikita.lab2.service.exception.NoAccountFoundException;
import ru.nikita.lab2.service.exception.NoUserFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountCRUDService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Account createAccount(UUID userId) {
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException(userId));

        var accountEntity = new AccountEntity(0, userEntity);
        AccountEntity savedEntity = accountRepository.save(accountEntity);

        return toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void removeAccount(UUID accountId) {
        AccountEntity entity = accountRepository.findById(accountId)
                .orElseThrow(NoAccountFoundException::new);
        accountRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(UUID accountId) {
        AccountEntity entity = accountRepository.findById(accountId)
                .orElseThrow(NoAccountFoundException::new);
        return toDomain(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccounts(){
        return accountRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsByUserId(UUID userId){
        if(!userRepository.existsById(userId)){
            throw new NoUserFoundException(userId);
        }
        return accountRepository.findAllUsersById(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Account toDomain(AccountEntity entity){
        return new Account(
                entity.getId(),
                entity.getUser().getId(),
                entity.getBalance()
        );
    }
}