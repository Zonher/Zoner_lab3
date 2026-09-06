package ru.nikita.lab2.api.dto;

import java.util.UUID;

public record FriendDto(UUID userId, UUID friendId) {
}
