package ru.nikita.lab2.api.dto;

import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;

import java.util.UUID;

public record UserDto(UUID userId, String login, String name, int age, Gender gender, HairColor hairColor) {
}
