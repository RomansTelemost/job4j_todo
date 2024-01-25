package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Repository
public class TaskStore implements TaskRepository {

    private final CrudRepository cr;
    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class.getName());

    @Override
    public List<Task> findAll() {
        return cr.query("FROM Task t join fetch t.priority ORDER BY t.id", Task.class);
    }

    @Override
    public List<Task> findDoneTasks() {
        return cr.query("FROM Task t join fetch t.priority WHERE done is true ORDER BY t.id", Task.class);
    }

    @Override
    public List<Task> findNewTasks() {
        return cr.query("FROM Task t join fetch t.priority WHERE done is false ORDER BY t.id", Task.class);
    }

    @Override
    public Optional<Task> save(Task task) {
        cr.run(session -> session.persist(task));
        return Optional.of(task);

    }

    @Override
    public boolean update(Task task) {
        try {
            cr.run(session -> session.merge(task));
            return true;
        } catch (Exception e) {
            LOG.error("Error while create task", e);
        }
        return false;
    }

    @Override
    public Optional<Task> findById(int id) {
        return cr.optional("FROM Task t join fetch t.priority WHERE t.id = :pId",
                Task.class,
                Map.of("pId", id));
    }

    @Override
    public boolean deleteById(int id) {
        Function<Session, Boolean> command = session ->
                session.createQuery("DELETE FROM Task WHERE id = :taskId")
                        .setParameter("taskId", id)
                        .executeUpdate() != 0;
        return cr.tx(command);
    }

    @Override
    public boolean completeTask(int id) {
        Function<Session, Boolean> command = session ->
                session.createQuery("UPDATE Task SET done = true WHERE id = :taskId")
                        .setParameter("taskId", id)
                        .executeUpdate() != 0;
        return cr.tx(command);
    }
}
