package ru.job4j.carprice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(ImageController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpg");
        String path = req.getRequestURI();
        logger.debug(path);
        ServletOutputStream outStream;
        outStream = resp.getOutputStream();
        FileInputStream fin = new FileInputStream(getServletContext().getInitParameter("ImageSrc")
        + path.substring("/image/".length()));
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(outStream);
        int data;
        while ((data = bin.read()) != -1) {
            bout.write(data);
        }
        bin.close();
        fin.close();
        bout.close();
        outStream.close();
    }
}
