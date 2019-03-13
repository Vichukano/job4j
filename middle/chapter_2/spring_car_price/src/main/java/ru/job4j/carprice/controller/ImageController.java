package ru.job4j.carprice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controller for loading image to client.
 */
@RestController
public class ImageController {
    private final Logger logger = LogManager.getLogger(ImageController.class);

    @Autowired
    private ServletContext context;

    /**
     * Method for loading image to view.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @GetMapping(value = "/api/image/**")
    public void loadImage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("image/jpg");
        String path = req.getRequestURI();
        logger.debug(path);
        if ((path.substring("/api/image/".length()).equals("empty"))) {
            logger.debug("EMPTY");
            return;
        }
        ServletOutputStream outStream = null;
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            outStream = resp.getOutputStream();
            fin = new FileInputStream(
                    this.context.getInitParameter("ImageSrc")
                            + path.substring("/api/image/".length())
            );
            bin = new BufferedInputStream(fin);
            bout = new BufferedOutputStream(outStream);
            int data;
            while ((data = bin.read()) != -1) {
                bout.write(data);
            }
        } catch (IOException e) {
            logger.error("IO exception: {}", e.getMessage());
            throw e;
        } finally {
            if (bout != null) {
                bout.close();
            }
            if (bin != null) {
                bin.close();
            }
            if (fin != null) {
                fin.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
    }

}
