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

@WebServlet(name = "MyServlet", urlPatterns = "/my-servlet")
public class Myservlet extends HttpServlet {

    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        String bookName = req.getParameter("name");
        String ISBN = req.getParameter("isbn");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();;
        String jsonResponse=null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkQuery = "select * from books where isbn = ?";
            PreparedStatement ps = connection.prepareStatement(checkQuery);
            ps.setString(1, ISBN);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                 jsonResponse = "{\"status\":\"faild\",\"message\":\"Book is already present.\"}";
                
                


            } else {
            

            String query = "INSERT INTO books (name, isbn) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookName);
            statement.setString(2, ISBN);
            statement.executeUpdate();
            jsonResponse = "{\"status\":\"sucess\",\"message\":\"Book is Added.\"}";
                
                
            }
            out.print(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        System.out.println("servlet sucessfull..");
    }

}
