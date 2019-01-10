package ru.job4j.presentation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * authentication filter class.
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (request.getRequestURI().contains("/signin")
                || request.getRequestURI().contains("/registration")
                || request.getRequestURI().contains("/cities")) {
            chain.doFilter(req, resp);
        } else {
            if (session.getAttribute("login") == null) {
                req.getRequestDispatcher("/signin").forward(req, resp);
                return;
            }
            chain.doFilter(req, resp);
        }
    }
}
