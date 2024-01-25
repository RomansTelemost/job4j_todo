package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore implements UserRepository {

    private final CrudRepository cr;

    @Override
    public Optional<User> findById(int id) {
        return cr.optional(
                "FROM User WHERE id = :pId",
                User.class,
                Map.of("pId", id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return cr.optional(
                "FROM User WHERE login = :pLogin",
                User.class,
                Map.of("pLogin", login));
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return cr.optional(
                "FROM User WHERE login = :pLogin and password = :pPassword",
                User.class,
                Map.of("pLogin", login, "pPassword", password));
    }

    @Override
    public Optional<User> save(User user) {
        cr.run(session -> session.persist(user));
        return Optional.of(user);
    }
}
