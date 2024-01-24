package ru.job4j.todo.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            String loginPageUrl = request.getContextPath() + "/user/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isAlwaysPermitted(String uri) {
        return uri.startsWith("/user/register")
                || uri.startsWith("/user/login")
                || uri.startsWith("/index")
                || uri.startsWith("/task/all")
                || uri.startsWith("/task/done")
                || uri.startsWith("/task/new")
                || uri.startsWith("/task/view")
                || uri.startsWith("/image")
                || uri.equals("/")
                || uri.startsWith("/js")
                || uri.startsWith("/css");
    }
}
