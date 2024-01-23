package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "redirect:/index";
    }

    @GetMapping("/done")
    public String getDoneTask(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        return "redirect:/index";
    }

    @GetMapping("/create")
    public String create() {
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model, BindingResult bindingResult) {
        try {
            taskService.save(task);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("massage", e.getMessage());
            return "error/404";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error/404";
        }
        model.addAttribute("task", taskOpt.get());
        return "task/edit";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable int id, Model model) {
        boolean isCompleted = taskService.completeTask(id);
        if (!isCompleted) {
            model.addAttribute("message", "Задача не была обновлена");
            return "error/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/new")
    public String getNotDoneTask(Model model) {
        model.addAttribute("tasks", taskService.findNewTasks());
        return "redirect:/index";
    }

    @GetMapping("/view/{id}")
    public String getTaskViewById(@PathVariable int id, Model model) {
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error/404";
        }
        model.addAttribute("task", taskOpt.get());
        return "task/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/index";
    }
}
