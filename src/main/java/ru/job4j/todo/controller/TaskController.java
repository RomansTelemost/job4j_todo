package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
}
