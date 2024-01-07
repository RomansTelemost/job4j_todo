package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

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
            Query<Task> query = session.createQuery("FROM Task", Task.class);
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
}
