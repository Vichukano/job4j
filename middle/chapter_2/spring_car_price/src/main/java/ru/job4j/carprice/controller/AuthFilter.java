package ru.job4j.carprice.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (req.getRequestURI().contains("/login")
                || req.getRequestURI().contains("/reg")) {
            chain.doFilter(request, response);
        } else {
            if (session.getAttribute("login") == null) {
                request.getRequestDispatcher("/login").forward(request, response);
                return;
            }
            chain.doFilter(request, response);
        }
    }
}
