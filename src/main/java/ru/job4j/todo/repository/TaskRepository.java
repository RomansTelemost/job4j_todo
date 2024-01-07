package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();
}
