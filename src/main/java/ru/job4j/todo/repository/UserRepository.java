package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(int id);

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> save(User user);

}
