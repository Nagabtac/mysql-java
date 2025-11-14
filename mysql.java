import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner; // Import the Scanner class

public class mysql {

    // --- Database connection ---
    static String url = "jdbc:mysql://localhost:3310/shop_db";
    static String user = "root";
    static String password = "admin";


    public static void getAllCustomers(Connection conn) throws Exception {
        System.out.println("\n--- Reading data from database ---");
        // Create statement and result set
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from customers");


        while (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            String gender = rs.getString("gender");
            String birthDate = rs.getString("birth_date");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Gender: " + gender);
            
            System.out.println("Birth Date: " + birthDate);
            System.out.println();
        }

        // Close resources
        rs.close();
        stmt.close();
    }

    // --- 2. INSERT Method ---
    public static void insertCustomer(Connection conn, Scanner scanner) throws Exception {
        System.out.println("\n--- Inserting new customer ---");
        
        // Get input from user
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter gender (M/F): ");
        String gender = scanner.nextLine();
        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        String sql = "INSERT INTO customers (name, email, gender, birth_date) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, gender);
        ps.setString(4, birthDate);
        
        ps.executeUpdate();
        ps.close();
        System.out.println("Data inserted successfully");
    }

    // --- 3. UPDATE Method ---
    public static void updateCustomer(Connection conn, Scanner scanner) throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from customers");


        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String gender = rs.getString("gender");
            String birthDate = rs.getString("birth_date");
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Gender: " + gender);
            
            System.out.println("Birth Date: " + birthDate);
            System.out.println();
        }

        // Close resources
        rs.close();
        stmt.close();
        System.out.println("\n--- Updating customer ---");

        // Get input from user
        System.out.print("Enter customer ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        String updateString = "UPDATE customers SET name = ? WHERE id = ?";
        PreparedStatement up = conn.prepareStatement(updateString);
        up.setString(1, newName);
        up.setInt(2, id);
        
        up.executeUpdate();
        up.close();
        System.out.println("Data updated successfully");
    }

    // --- 4. DELETE Method ---
    public static void deleteCustomer(Connection conn, Scanner scanner) throws Exception {
        System.out.println("\n--- Deleting customer ---");
        
        // Get input from user
        System.out.print("Enter customer ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        String deleteString = "DELETE FROM customers WHERE id = ?";
        PreparedStatement deleteps = conn.prepareStatement(deleteString);
        deleteps.setInt(1, id);
        
        deleteps.executeUpdate();
        deleteps.close();
        System.out.println("Data deleted successfully");
    }


    // --- MAIN Method (Menu Controller) ---
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        // The single try/catch block from your original code
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(url, user, password);
            
            boolean running = true;
            while(running) { //while loop for menu. running variable controls the loop if user wants to exit just choose 5
                System.out.println("\n---MENU---");
                System.out.println("1. View all customers");
                System.out.println("2. Insert a new customer");
                System.out.println("3. Update a customer's name");
                System.out.println("4. Delete a customer");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 1) {
                    getAllCustomers(conn);
                } else if (choice == 2) {
                    insertCustomer(conn, scanner);
                } else if (choice == 3) {
                    updateCustomer(conn, scanner);
                } else if (choice == 4) {
                    deleteCustomer(conn, scanner);
                } else if (choice == 5) {
                    running = false;
                    System.out.println("Exiting program.");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        scanner.close(); 
    }
}


// To compile and run
// javac -cp ".;mysql-connector-j-9.5.0.jar" mysql.java
// java -cp ".;mysql-connector-j-9.5.0.jar" mysql