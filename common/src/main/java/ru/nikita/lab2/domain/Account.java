package ru.nikita.lab2.domain;

import java.util.UUID;

public record Account(
        UUID id,
        UUID userId,
        double balance) {

}
