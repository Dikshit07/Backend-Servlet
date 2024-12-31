package backend.demo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class home extends HttpServlet
{
   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        System.out.println("Request URI: " + req.getRequestURI());
        System.out.println("Request URL: " + req.getRequestURL());
        System.out.println("Context Path: " + req.getContextPath());
        // req.getRequestDispatcher("/static/home.jsp").forward(req, resp);
        resp.sendRedirect(req.getContextPath() + "/home.html");

    }
}
