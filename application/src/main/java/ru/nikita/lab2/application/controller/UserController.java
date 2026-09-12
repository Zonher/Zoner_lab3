package ru.nikita.lab2.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;
import ru.nikita.lab2.application.dto.UserCreateRequest;
import ru.nikita.lab2.application.dto.UserResponse;
import ru.nikita.lab2.application.dto.UserUpdateRequest;
import ru.nikita.lab2.application.mapper.ApiMapper;
import ru.nikita.lab2.service.AccountCRUDService;
import ru.nikita.lab2.service.FriendService;
import ru.nikita.lab2.service.UserCRUDService;
import ru.nikita.lab2.service.impl.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserCRUDService userService;
    private final FriendService friendService;
    private final ApiMapper mapper;

    public UserController(UserCRUDService userService, FriendService friendService, ApiMapper mapper){
        this.userService = userService;
        this.friendService = friendService;
        this.mapper = mapper;
    }

    @Operation(summary = "Создать пользователя")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Пользователь создан"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
            ){
        var user = userService.createUser(mapper.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(user));
    }

    @Operation(summary = "Получить пользователя по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable UUID userId){
        return mapper.toResponse(userService.getUser(userId));
    }

    @Operation(summary = "Получить всех пользователей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей"),
            @ApiResponse(responseCode = "400", description = "Некорректный фильтр"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public List<UserResponse> getUsers(
            @RequestParam(required = false) HairColor hairColor,
            @RequestParam(required = false)Gender gender
            ){
        return userService.getUsers(hairColor, gender)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Operation(summary = "Обновить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь ненайден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PutMapping("/{userId}")
    public UserResponse updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UserUpdateRequest request
            ){
        return mapper.toResponse(
                userService.updateUser(mapper.toDomain(userId, request))
        );
    }

    @Operation(summary = "Получить друзей пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список друзкй"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{userId}/friends")
    public List<UserResponse> getFriends(@PathVariable UUID userId){
        return friendService.getFriends(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Operation(summary = "Добавить друга")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Друг добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(
            @PathVariable UUID userId,
            @PathVariable UUID friendId
    ){
        friendService.addFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить друга")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Друг удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(
            @PathVariable UUID userId,
            @PathVariable UUID friendId
    ){
        friendService.removeFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

}
