package ru.nikita.lab2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record AmountRequest(
        @Schema(description = "Сумма операции", example = "500.0")
        @Positive
        double amount
) {
}