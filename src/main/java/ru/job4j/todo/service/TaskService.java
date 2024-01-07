package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

}
