package ru.job4j.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Update filter class.
 * Admin can update all users.
 * User can update only itself.
 */
public class UpdateFilter implements Filter {
    private final Logger logger = LogManager.getLogger(UpdateFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (!session.getAttribute("role").equals("Admin")) {
            logger.debug(session.getAttribute("role"));
            if (!session.getAttribute("id").toString().equals(req.getParameter("id"))) {
                logger.debug(session.getAttribute("id") + " " + req.getParameter("id"));
                req.getRequestDispatcher("/").forward(req, resp);
                return;
            }
            chain.doFilter(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
