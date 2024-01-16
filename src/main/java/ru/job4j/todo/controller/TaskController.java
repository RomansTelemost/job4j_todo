package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/done")
    public String getDoneTask(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        return "index";
    }

    @GetMapping("/create")
    public String create() {
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("massage", e.getMessage());
            return "error/404";
        }
    }

    @RequestMapping(method=RequestMethod.POST, value = "/update")
    public String update(@ModelAttribute Task task, Model model) {
        try {
            boolean isUpdated = taskService.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Задача с указанным идентификатором не найдена");
                return "error/404";
            }
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error/404";
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/{id}")
    public String getById(@PathVariable int id, Model model) {
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error/404";
        }
        model.addAttribute("task", taskOpt.get());
        return "task/edit";
    }

    @GetMapping("/new")
    public String getNotDoneTask(Model model) {
        model.addAttribute("tasks", taskService.findNewTasks());
        return "index";
    }

    @RequestMapping(method=RequestMethod.GET, value = "/view/{id}")
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
