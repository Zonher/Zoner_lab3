package ru.nikita.lab2.application.mapper;

import org.springframework.stereotype.Component;
import ru.nikita.lab2.application.dto.*;
import ru.nikita.lab2.domain.Account;
import ru.nikita.lab2.domain.Operation;
import ru.nikita.lab2.domain.User;

@Component
public class ApiMapper {
    public User toDomain(UserCreateRequest request){
        return new User(
                null,
                request.login(),
            request.name(),
            request.age(),
            request.gender(),
            request.hairColor()
        );
    }

    public User toDomain(java.util.UUID userId, UserUpdateRequest request){
        return new User(
                userId,
                null,
                request.name(),
                request.age(),
                request.gender(),
                request.hairColor()
        );
    }

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.id(),
                user.login(),
                user.name(),
                user.age(),
                user.gender(),
                user.hairColor()
        );
    }

    public AccountResponse toResponse(Account account){
        return new AccountResponse(
                account.id(),
                account.userId(),
                account.balance()
        );
    }

    public OperationResponse toResponse(Operation operation){
        return new OperationResponse(
                operation.id(),
                operation.accountId(),
                operation.opType(),
                operation.amount(),
                    operation.commission(),
                operation.operationInstant()
        );
    }
}
