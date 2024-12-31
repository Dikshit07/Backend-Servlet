package backend.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FetchBooksServlet", urlPatterns = "/fetch-books")
public class FetchBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse;

        try (PrintWriter out = resp.getWriter();
             Connection connection = DatabaseConnection.getConnection()) {

            
            String selectQuery = "SELECT isbn, name FROM books";
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("["); // Start of JSON Array

            boolean hasRecords = false;

            while (rs.next()) {
                hasRecords = true;

               
                String isbn = rs.getString("isbn");
                String name = rs.getString("name");
                jsonBuilder.append("{");
                jsonBuilder.append("\"isbn\":\"").append(isbn).append("\",");
                jsonBuilder.append("\"name\":\"").append(name).append("\",");
                jsonBuilder.append("},");

            }

            if (hasRecords) {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); 
            }

            jsonBuilder.append("]"); 

            jsonResponse = jsonBuilder.toString();

            
            out.print(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"status\":\"error\", \"message\":\"Failed to fetch data.\"}";

            try (PrintWriter out = resp.getWriter()) {
               
                out.print(jsonResponse);
            }
        }
    }
}