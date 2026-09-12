package ru.nikita.lab2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.nikita.lab2.api.enumeration.OpType;

import java.time.Instant;
import java.util.UUID;

public record OperationResponse(
        @Schema(description = "ID операции")
        UUID id,

        @Schema(description = "ID счёта")
        UUID accountId,

        @Schema(description = "Тип операции")
        OpType opType,

        @Schema(description = "Сумма операции")
        double amount,

        @Schema(description = "Комиссия")
        double commission,

        @Schema(description = "Время операции")
        Instant operationInstant
) {
}