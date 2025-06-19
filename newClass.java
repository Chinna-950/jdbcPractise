package jdbc;
import java.sql.*;

public class newClass {
    public static void main(String[] args) throws SQLException{

        // SQL queries
        String insertQuery = "INSERT INTO student (id, name) VALUES (?, ?)";
        String selectQuery = "SELECT * FROM student";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Anirudh@950");

            // Insert data using PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "John Doe");
            pstmt.executeUpdate();

            // Retrieve data
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);

            System.out.println("Student Records:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(id + ": " + name);
            }

            // Close all connections
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
