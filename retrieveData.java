package jdbc;

import java.sql.*;

public class retrieveData {

    public static void main(String[] args) {
        // JDBC variables
        String url = "jdbc:mysql://localhost:3306/mydb"; // Change DB name
        String user = "root"; // Change username
        String password = "Anirudh@999"; // Change password

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            conn = DriverManager.getConnection(url, user, password);

            // Create statement
            stmt = conn.createStatement();

            // Execute query
            String sql = "SELECT * FROM students";  // Your table name
            rs = stmt.executeQuery(sql);

            // Process result
            while (rs.next()) {
                int id = rs.getInt("id"); // change column names as per your table
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
