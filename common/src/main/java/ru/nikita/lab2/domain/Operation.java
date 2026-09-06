package ru.nikita.lab2.domain;

import ru.nikita.lab2.api.enumeration.OpType;

import java.time.Instant;
import java.util.UUID;

public record Operation(
        UUID id,
        UUID accountId,
        OpType opType,
        double amount,
        double commission,
        Instant operationInstant
) {

}
