import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        String URL = "jdbc:postgresql://localhost:5432/pos";
        String USER = "user";
        String PASSWORD = "password";

        // Simple test to see if the database is working
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
