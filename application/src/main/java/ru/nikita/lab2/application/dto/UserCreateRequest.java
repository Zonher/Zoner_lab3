package ru.nikita.lab2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;

public record UserCreateRequest(
        @Schema(description = "Логин юзера", example = "nikita")
        @NotBlank
        String login,

        @Schema(description = "Имя юзера", example = "Nikita")
        @NotBlank
        String name,

        @Schema(description = "Возраст юзера", example = "20")
        @Min(1)
        int age,

        @Schema(description = "Пол юзера")
        @NotNull
        Gender gender,

        @Schema(description = "Цвет волос")
        @NotNull
        HairColor hairColor
) {
}