package ru.nikita.lab2.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.nikita.lab2.api.dto.FriendDto;
import ru.nikita.lab2.dao.entity.UserEntity;
import ru.nikita.lab2.dao.repository.UserRepository;
import ru.nikita.lab2.domain.User;
import ru.nikita.lab2.service.FriendService;
import ru.nikita.lab2.service.exception.NoUserFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;

    public FriendServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addFriend(UUID userId, UUID friendId) {
        if (userId.equals(friendId)){
            throw new IllegalArgumentException("User cannot be friend with himself");
        }
        UserEntity user = findUser(userId);
        UserEntity friend = findUser(friendId);

        var userFriends = new HashSet<>(user.getFriends());
        userFriends.add(friend);
        user.setFriends(userFriends);

        var friendFriends = new HashSet<>(friend.getFriends());
        friendFriends.add(user);
        friend.setFriends(friend.getFriends());
    }

    @Override
    @Transactional
    public void removeFriend(UUID userId, UUID friendId) {
        UserEntity user = findUser(userId);
        UserEntity friend = findUser(friendId);

        var userFriends = new HashSet<>(user.getFriends());
        userFriends.remove(friend);
        friend.setFriends(userFriends);

        var friendFriends = new HashSet<>(friend.getFriends());
        friendFriends.remove(user);
        friend.setFriends(friendFriends);

        userRepository.save(user);
        userRepository.save(friend);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFriends(UUID userId){
        return findUser(userId)
                .getFriends()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private UserEntity findUser(UUID userId){
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NoUserFoundException(userId));
    }

    private User toDomain(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getLogin(),
                entity.getName(),
                entity.getAge(),
                entity.getGender(),
                entity.getHairColor()
        );
    }
}
