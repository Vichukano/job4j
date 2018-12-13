package ru.job4j.presentation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Delete filter class.
 * Cannot delete user if you are not a admin.
 */
public class DeleteFilter implements Filter {

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
