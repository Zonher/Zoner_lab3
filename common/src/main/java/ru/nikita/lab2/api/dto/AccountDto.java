package ru.nikita.lab2.api.dto;

import java.util.UUID;

public record AccountDto(UUID accountId, UUID userId) {
}
