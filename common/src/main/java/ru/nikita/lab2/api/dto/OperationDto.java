package ru.nikita.lab2.api.dto;

import ru.nikita.lab2.api.enumeration.OpType;

import java.util.UUID;

public record OperationDto(OpType opType, UUID fromAcc, UUID toAcc, double amount) {
}
