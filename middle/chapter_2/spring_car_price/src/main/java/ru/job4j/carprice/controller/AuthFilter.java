package ru.job4j.carprice.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Deprecated
 * @see ru.job4j.carprice.configuration.WebSecurityConfig
 */
@Component
//@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (req.getRequestURI().contains("/login")
                || req.getRequestURI().contains("/reg")) {
            chain.doFilter(request, response);
        } else {
            if (session.getAttribute("login") == null) {
                resp.sendRedirect("/login");
                return;
            }
            chain.doFilter(request, response);
        }
    }
}
