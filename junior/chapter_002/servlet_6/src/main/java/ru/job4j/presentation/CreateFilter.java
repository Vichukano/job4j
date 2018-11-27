package ru.job4j.presentation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter class for creating users.
 * Cannot create new user if you not a admin.
 */
public class CreateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (!session.getAttribute("role").equals("Admin")) {
            req.getRequestDispatcher("/").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }
}
