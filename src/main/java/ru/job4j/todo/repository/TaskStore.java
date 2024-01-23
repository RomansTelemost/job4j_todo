package ru.job4j.todo.repository;

import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements TaskRepository {
    private final SessionFactory sf;
    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class.getName());

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task ORDER BY id", Task.class);
            tasks = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while findAll", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public List<Task> findDoneTasks() {
        Session session = sf.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task WHERE done is true ORDER BY id", Task.class);
            tasks = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while findDoneTasks", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public List<Task> findNewTasks() {
        Session session = sf.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task WHERE done is false ORDER BY id", Task.class);
            tasks = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while findDoneTasks", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Optional<Task> save(Task task) {
        Session session = sf.openSession();
        Optional<Task> taskOptional = Optional.empty();
        try {
            session.beginTransaction();
            session.save(task);
            taskOptional = Optional.of(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while create task", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskOptional;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            int effectedRows = session.createQuery(
                    "UPDATE Task SET title = :pTitle, description = :pDescription WHERE id = :pId")
                    .setParameter("pId", task.getId())
                    .setParameter("pTitle", task.getTitle())
                    .setParameter("pDescription", task.getDescription())
                    .executeUpdate();
            session.getTransaction().commit();
            result = effectedRows >= 1;
        } catch (Exception e) {
            LOG.error("Error while create task", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> taskOpt = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "FROM Task WHERE id = :pId", Task.class);
            query.setParameter("pId", id);
            taskOpt = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error while create task", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskOpt;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Task WHERE id = :taskId");
            query.setParameter("taskId", id);
            query.executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            LOG.error("Error while delete task with id " + id, e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean completeTask(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Task SET done = true WHERE id = :taskId");
            query.setParameter("taskId", id);
            query.executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            LOG.error("Error while delete task with id " + id, e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
