package ru.nikita.lab2.service;

import ru.nikita.lab2.api.dto.FriendDto;
import ru.nikita.lab2.domain.User;

import java.util.List;
import java.util.UUID;

public interface FriendService {
    void addFriend(UUID userId, UUID friendId);
    void removeFriend(UUID userId, UUID friendID);
    List<User> getFriends(UUID userId);
}
