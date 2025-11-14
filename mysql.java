import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class mysql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3310/shop_db";
        String user = "root";
        String password = "admin";

        try {
            // Load JDBC driver (optional for JDBC 4.0+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from customers");

            while (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("\n");
            }



            // System.out.println("\ninserting data into database");
            // String sql = "INSERT INTO customers (name, email, gender, birth_date) VALUES (?, ?, ?, ?)";

            // PreparedStatement ps = conn.prepareStatement(sql);
            // ps.setString(1, "Lara");
            // ps.setString(2, "laracrus@example.com");
            // ps.setString(3, "F");
            // ps.setString(4, "1990-01-01");
            // ps.executeUpdate();
            // System.out.println("Data inserted successfully");



            String updateString = "UPDATE customers SET gender = ? WHERE id = ?";

            PreparedStatement up = conn.prepareStatement(updateString);
            up.setString(1, "F");
            up.setInt(2, 3);
            up.executeUpdate();
            System.out.println("Data updated successfully");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//To compile and run
//javac -cp ".;mysql-connector-j-9.5.0.jar" mysql.java
//java -cp ".;mysql-connector-j-9.5.0.jar" mysql