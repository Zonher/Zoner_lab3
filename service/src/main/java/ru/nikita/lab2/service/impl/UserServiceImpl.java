package ru.nikita.lab2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.lab2.api.enumeration.Gender;
import ru.nikita.lab2.api.enumeration.HairColor;
import ru.nikita.lab2.dao.entity.UserEntity;
import ru.nikita.lab2.dao.repository.UserRepository;
import ru.nikita.lab2.domain.User;
import ru.nikita.lab2.service.UserCRUDService;
import ru.nikita.lab2.service.exception.NoUserFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserCRUDService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        UserEntity entity = toEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        UserEntity entity = userRepository.findUserById(user.id())
                .orElseThrow(() -> new NoUserFoundException(user.id()));
        entity.setName(user.name());
        entity.setAge(user.age());
        entity.setGender(user.gender());
        entity.setHairColor((user.hairColor()));

        UserEntity savedEntity = userRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void removeUser(UUID userId) {
        UserEntity entity = userRepository.findUserById(userId)
                .orElseThrow(() -> new NoUserFoundException(userId));
        userRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(UUID userId) {
        UserEntity entity = userRepository.findUserById(userId)
                .orElseThrow(() -> new NoUserFoundException(userId));
        return toDomain(entity);
    }

    @Override
    @Transactional
    public List<User> getUsers(HairColor hairColor, Gender gender){
        return userRepository.findFiltered(hairColor, gender)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.id())
                .login(user.login())
                .name(user.name())
                .age(user.age())
                .gender(user.gender())
                .hairColor(user.hairColor())
                .build();
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
