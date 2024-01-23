package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpServletRequest request, Model model) {
        var userOpt = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "login or password isn't correct");
            return "user/login";
        }
        request.getSession().setAttribute("user", userOpt.get());
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpServletRequest request, Model model) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Login is already taken");
            return "errors/404";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

}
