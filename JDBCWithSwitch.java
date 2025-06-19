package jdbc;
import java.sql.*;
import java.util.Scanner;

public class JDBCWithSwitch {

    static final String URL = "jdbc:mysql://localhost:3306/mydb"; // use your DB name
    static final String USER = "root";
    static final String PASSWORD = "Anirudh@950";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\nChoose an operation:");
                System.out.println("1. Create Table");
                System.out.println("2. Insert Data");
                System.out.println("3. Update Data");
                System.out.println("4. Retrieve Data");
                System.out.println("5. Delete Data");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        createTable(conn);
                        break;
                    case 2:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        insertData(conn, id, name, age);
                        break;
                    case 3:
                        System.out.print("Enter ID to update: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter new Age: ");
                        int newAge = sc.nextInt();
                        updateData(conn, uid, newAge);
                        break;
                    case 4:
                        retrieveData(conn);
                        break;
                    case 5:
                        System.out.print("Enter ID to delete: ");
                        int did = sc.nextInt();
                        deleteData(conn, did);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } while (choice != 6);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INT PRIMARY KEY, " +
                     "name VARCHAR(50), " +
                     "age INT)";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        System.out.println("Table created (if not existed).");
    }

    public static void insertData(Connection conn, int id, String name, int age) throws SQLException {
        String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.executeUpdate();
        System.out.println("Data inserted successfully.");
    }

    public static void updateData(Connection conn, int id, int age) throws SQLException {
        String sql = "UPDATE students SET age = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, age);
        ps.setInt(2, id);
        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Data updated successfully.");
        else
            System.out.println("ID not found.");
    }

    public static void retrieveData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- Student Records ---");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                               ", Name: " + rs.getString("name") +
                               ", Age: " + rs.getInt("age"));
        }
    }

    public static void deleteData(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Data deleted successfully.");
        else
            System.out.println("ID not found.");
    }
}
