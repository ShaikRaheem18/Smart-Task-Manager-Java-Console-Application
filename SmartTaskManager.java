import java.sql.*;
import java.util.Scanner;

public class SmartTaskManager {
    static final String DB_URL = "jdbc:mysql://localhost:3306/task_manager";
    static final String USER = "root"; // Change if needed
    static final String PASS = "your_password"; // Change to your MySQL password
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            while (true) {
                System.out.println("\n===== SMART TASK MANAGER =====");
                System.out.println("1. View All Tasks");
                System.out.println("2. Add New Task");
                System.out.println("3. Update Task");
                System.out.println("4. Mark Task as Completed");
                System.out.println("5. Delete Task");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> viewTasks(conn);
                    case 2 -> addTask(conn);
                    case 3 -> updateTask(conn);
                    case 4 -> markCompleted(conn);
                    case 5 -> deleteTask(conn);
                    case 6 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void viewTasks(Connection conn) throws SQLException {
        String query = "SELECT * FROM tasks";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n--- All Tasks ---");
            while (rs.next()) {
                System.out.printf("[%d] %s | %s | Priority: %s | Due: %s | Status: %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getDate("due_date"),
                        rs.getString("status"));
            }
        }
    }

    static void addTask(Connection conn) throws SQLException {
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();
        System.out.print("Enter priority (Low/Medium/High): ");
        String priority = sc.nextLine();
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        String query = "INSERT INTO tasks (title, description, priority, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setString(3, priority);
            pstmt.setString(4, dueDate);
            pstmt.executeUpdate();
            System.out.println("Task added successfully!");
        }
    }

    static void updateTask(Connection conn) throws SQLException {
        System.out.print("Enter task ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new title: ");
        String title = sc.nextLine();
        System.out.print("Enter new description: ");
        String desc = sc.nextLine();
        System.out.print("Enter new priority (Low/Medium/High): ");
        String priority = sc.nextLine();
        System.out.print("Enter new due date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        String query = "UPDATE tasks SET title=?, description=?, priority=?, due_date=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setString(3, priority);
            pstmt.setString(4, dueDate);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Task updated successfully!");
        }
    }

    static void markCompleted(Connection conn) throws SQLException {
        System.out.print("Enter task ID to mark completed: ");
        int id = sc.nextInt();

        String query = "UPDATE tasks SET status='Completed' WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Task marked as completed!");
        }
    }

    static void deleteTask(Connection conn) throws SQLException {
        System.out.print("Enter task ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM tasks WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Task deleted successfully!");
        }
    }
}
