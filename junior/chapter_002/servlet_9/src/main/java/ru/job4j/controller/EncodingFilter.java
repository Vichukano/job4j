package ru.job4j.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getRequestURI().contains("customers")
                && req.getMethod().equalsIgnoreCase("GET")) {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            chain.doFilter(req, resp);
        } else if (req.getRequestURI().contains("delete")) {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            chain.doFilter(req, resp);
        } else if (req.getRequestURI().contains("payment")
                && req.getMethod().equalsIgnoreCase("GET")) {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            chain.doFilter(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
