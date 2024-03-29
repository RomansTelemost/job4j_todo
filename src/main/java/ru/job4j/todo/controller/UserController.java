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
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
            /**
             * В header отображается поле name User - под которым пытались залогиниться.
             * Т.е. name = "" поэтому не оторабражается кнопка register
             * Установим Guest user
             */
            user = new User();
            user.setName("Guest");
            user.setTimezone(TimeZone.getDefault().toString());
            model.addAttribute("user", user);
            model.addAttribute("error", "login or password isn't correct");
            return "user/login";
        }
        request.getSession().setAttribute("user", userOpt.get());
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("timezones", getDefaultTimeZones());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           HttpServletRequest request,
                           Model model) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            /**w
             * В header отображается поле name User - которого пытались зарегистрировать.
             * Установим Guest user
             */
            user = new User();
            user.setName("Guest");
            user.setTimezone(TimeZone.getDefault().toString());
            model.addAttribute("user", user);
            model.addAttribute("error", "Login is already taken");
            model.addAttribute("timezones", getDefaultTimeZones());
            return "user/register";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    private List<String> getDefaultTimeZones() {
        List<String> zones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId).getID());
        }
        return zones;
    }
}
