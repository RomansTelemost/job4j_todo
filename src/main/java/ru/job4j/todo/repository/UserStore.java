package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore implements UserRepository {

    private final SessionFactory sf;
    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class.getName());

    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM todo_user WHERE id = :pId", User.class);
            query.setParameter("pId", id);
            userOpt = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while get User", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "SELECT * FROM todo_user WHERE login = :login", User.class);
            query.setParameter("login", login);
            userOpt = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while find by login User", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User WHERE login = :pLogin and password = :pPassword", User.class);
            query.setParameter("pLogin", login);
            query.setParameter("pPassword", password);
            userOpt = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while find by login and password User", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            userOpt = Optional.of(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while save User", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }
}
