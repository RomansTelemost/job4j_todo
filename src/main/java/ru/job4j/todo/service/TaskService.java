package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    List<Task> findDoneTasks();

    List<Task> findNewTasks();

    Optional<Task> save(Task task);

    boolean update(Task task);

    Optional<Task> findById(int id);

    boolean deleteById(int id);

    boolean completeTask(int id);
}
