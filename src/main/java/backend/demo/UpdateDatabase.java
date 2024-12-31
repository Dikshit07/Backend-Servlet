package backend.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateBookServlet", urlPatterns = "/update-book")
public class UpdateDatabase extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String jsonResponse;
        String isbn = req.getParameter("isbn");
        String bookName = req.getParameter("name");
        try (PrintWriter out = resp.getWriter();
                Connection connection = DatabaseConnection.getConnection()) {
            if (isbn == null || isbn.isEmpty()) {
                jsonResponse = "{\"status\":\"failed\", \"message\":\"ISBN is required.\"}";
                out.print(jsonResponse);
                return;
            } else {
                if (bookName == null || bookName.isEmpty()) {
                    jsonResponse = "{\"status\":\"failed\", \"message\":\"Book name is required.\"}";
                    out.print(jsonResponse);
                    return;
                }

                String UpdateQuery = "UPDATE books  SET name = ? WHERE isbn = ?";
                PreparedStatement ps = connection.prepareStatement(UpdateQuery);
                ps.setString(1, bookName);
                ps.setString(2, isbn);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    jsonResponse = "{\"status\":\"success\", \"message\":\"Book name is Updated.\"}";
                } else {

                    jsonResponse = "{\"status\":\"failed\", \"message\":\"No book found with the given ISBN.\"}";
                }

                out.print(jsonResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"status\":\"error\", \"message\":\"An error occurred while deleting the book.\"}";
            try (PrintWriter out = resp.getWriter()) {
                out.print(jsonResponse);
            }
        }
    }
}
