package ru.nikita.lab2.service;

import ru.nikita.lab2.api.dto.UserDto;
import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;
import ru.nikita.lab2.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserCRUDService {
    User createUser(User user);
    User updateUser(User user);
    void removeUser(UUID userId);
    User getUser(UUID userId);
    List<User> getUsers(HairColor hairColor, Gender gender);
}
