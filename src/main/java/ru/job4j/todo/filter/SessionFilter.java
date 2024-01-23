package ru.job4j.todo.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.job4j.todo.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class SessionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = request.getSession();
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        request.setAttribute("user", user);
        chain.doFilter(request, response);
    }
}
