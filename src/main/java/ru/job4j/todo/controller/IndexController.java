package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.TaskService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final TaskService taskService;

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "/index";
    }
}
