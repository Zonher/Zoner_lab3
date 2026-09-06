package ru.nikita.lab2.service.exception;

import java.util.UUID;

public class NoUserFoundException extends RuntimeException {
    private static final String NO_USER_FOUND_MSG = "User not found: {id=%s}";

    public NoUserFoundException(UUID userId) {
        super(String.format(NO_USER_FOUND_MSG, userId));
    }
}
