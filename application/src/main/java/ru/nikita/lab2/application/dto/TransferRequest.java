package ru.nikita.lab2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UUID;

public record TransferRequest(
        @Schema(description = "ID счёта отправителя")
        @NotNull
        UUID fromAccountId,

        @Schema(description = "ID счёта получателя")
        @NotNull
        UUID toAccountId,

        @Schema(description = "Сумма перевода", example = "100.0")
        @Positive
        double amount
) {
}