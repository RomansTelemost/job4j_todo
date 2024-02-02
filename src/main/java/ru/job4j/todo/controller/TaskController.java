package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.TimeFormatter;

import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAll(@SessionAttribute(required = false) User user, Model model) {
        model.addAttribute("tasks", TimeFormatter.convertTimeByTimezone(taskService.findAll(), user));
        return "/index";
    }

    @GetMapping("/done")
    public String getDoneTask(@SessionAttribute(required = false) User user, Model model) {
        model.addAttribute("tasks", TimeFormatter.convertTimeByTimezone(taskService.findDoneTasks(), user));
        return "/index";
    }

    @GetMapping("/new")
    public String getNotDoneTask(@SessionAttribute(required = false) User user, Model model) {
        model.addAttribute("tasks", TimeFormatter.convertTimeByTimezone(taskService.findNewTasks(), user));
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task,
                         @SessionAttribute("user") User user,
                         @RequestParam(required = false) Set<Integer> cIds) {
        task.setUser(user);
        if (!cIds.isEmpty()) {
            task.setCategories(categoryService.findByIds(cIds));
        }
        if (taskService.save(task).isEmpty()) {
            return "error/404";
        }
        return "redirect:/index";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task,
                         @SessionAttribute("user") User user,
                         @RequestParam(required = false) Set<Integer> cIds,
                         Model model) {
        task.setUser(user);
        if (!cIds.isEmpty()) {
            task.setCategories(categoryService.findByIds(cIds));
        }
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
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
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

    @GetMapping("/view/{id}")
    public String getTaskViewById(@PathVariable int id, Model model) {
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error/404";
        }
        model.addAttribute("task", taskOpt.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
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
