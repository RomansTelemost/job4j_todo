package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.repository.TaskRepository;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;
}
