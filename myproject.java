
import java.sql.*;
import java.util.*;

public class student {
	
    static final String URL = "jdbc:mysql://localhost:3306/myproject";
    static final String USER = "root";  
    static final String PASS = "password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void addStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO students (name,fathername, email, gender, date_of_birth, course) VALUES (?, ?,?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter Name: ");
            ps.setString(1, sc.nextLine());
            System.out.println("enter fathername:");
            ps.setString(2,sc.nextLine());
            System.out.print("Enter Email: ");
            ps.setString(3, sc.nextLine());
           
            System.out.print("Enter Gender (M/F/O): ");
            ps.setString(4, sc.nextLine());
            System.out.print("Enter DOB (yyyy-mm-dd): ");
            ps.setString(5, sc.nextLine());
            System.out.print("Enter Course: ");
            ps.setString(6, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Student Added Successfully!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void viewStudents() {
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                System.out.printf("%d | %s | %s |%s| %s | %s | %s | %s%n",
                        rs.getInt("id"), rs.getString("name"),rs.getString("fathername"), rs.getString("email"),
                        rs.getString("gender"), rs.getString("date_of_birth"),
                        rs.getString("course"), rs.getString("created_at"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void updateStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE students SET name=?,fathername=?, email=?,gender=?,date_of_birth=?, course=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter Student ID to Update: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.print("Enter New Name: ");
            ps.setString(1, sc.nextLine());
            System.out.print("Enter New fathername: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Enter New Email: ");
            ps.setString(3, sc.nextLine());
            System.out.print("Enter New gender: ");
            ps.setString(4, sc.nextLine());
            System.out.print("Enter New date_of_birth: ");
            ps.setString(5, sc.nextLine());
            System.out.print("Enter New Course: ");
            ps.setString(6, sc.nextLine());
            ps.setInt(7, id);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student Updated!");
            else System.out.println("Student Not Found!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void deleteStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter Student ID to Delete: ");
            ps.setInt(1, sc.nextInt());

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student Deleted!");
            else System.out.println("Student Not Found!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
