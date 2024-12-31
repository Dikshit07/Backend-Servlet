package backend.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/user";
    private static final String uername = "root";
    private static final String password = "Emperor@0793";

     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, uername, password);
    }
    
}
