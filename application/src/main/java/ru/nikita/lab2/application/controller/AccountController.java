package ru.nikita.lab2.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nikita.lab2.application.dto.AccountCreateRequest;
import ru.nikita.lab2.application.dto.AccountResponse;
import ru.nikita.lab2.application.dto.AmountRequest;
import ru.nikita.lab2.application.mapper.ApiMapper;
import ru.nikita.lab2.service.AccountCRUDService;
import ru.nikita.lab2.service.OperationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountCRUDService accountService;
    private final OperationService operationService;
    private final ApiMapper apiMapper;

    public AccountController(
            AccountCRUDService accountService,
            OperationService operationService,
            ApiMapper apiMapper
    ){
        this.accountService = accountService;
        this.apiMapper = apiMapper;
        this.operationService = operationService;
    }

    @Operation(summary = "Создать счёт")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Счёт создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody AccountCreateRequest request
            ){
        return apiMapper.toResponse(
                accountService.createAccount(request.userId())
        );
    }

    @Operation(summary = "Получить все счета")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счета получены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public List<AccountResponse> getAccounts(){
        return accountService.getAccounts()
                .stream()
                .map(apiMapper::toResponse)
                .toList();
    }

    @Operation(summary = "Получить счёт по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счёт найден"),
            @ApiResponse(responseCode = "404", description = "Счёт не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{accountId}")
    public AccountResponse getAccount(
            @PathVariable UUID accountId
    ){
        return apiMapper.toResponse(accountService.getAccount(accountId));
    }

    @Operation(summary = "Получить счета пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счета пользователя получены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/user/{userId}")
    public List<AccountResponse> getAccountsByUserId(
            @PathVariable UUID userId
    ){
        return accountService.getAccountsByUserId(userId)
                .stream()
                .map(apiMapper::toResponse)
                .toList();
    }

    @Operation(summary = "Удалить счёт")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Счёт удалён"),
            @ApiResponse(responseCode = "404", description = "Счёт не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @PathVariable UUID accountId
    ){
        accountService.removeAccount(accountId);
    }

    @Operation(summary = "Пополнить счёт")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Счёт пополнен"),
            @ApiResponse(responseCode = "400", description = "Некоррректная сумма"),
            @ApiResponse(responseCode = "404", description = "Счёт не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/{accountId}/deposit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deposit(
            @PathVariable UUID accountId,
            @Valid @RequestBody AmountRequest request
            ){
        operationService.deposit(accountId, request.amount());
    }

    @Operation(summary = "Снять деньги со счёта")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Деньги сняты"),
            @ApiResponse(responseCode = "400", description = "Некорректная сумма или недостаточно денег"),
            @ApiResponse(responseCode = "404", description = "Счёт не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/{accountId}/withdraw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(
            @PathVariable UUID accountId,
            @Valid @RequestBody AmountRequest request
    ){
        operationService.withdraw(accountId, request.amount());
    }

}
