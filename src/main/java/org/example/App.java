package org.example;

import java.sql.*;
import java.time.LocalDate;

public class App
{

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT e.emp_no AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, t.title, s.salary, d.dept_name, dm.emp_no AS manager_employee_id "
                            + "FROM employees e"
                            + " INNER JOIN titles t ON e.emp_no = t.emp_no"
                            + " INNER JOIN salaries s ON e.emp_no = s.emp_no"
                            + " INNER JOIN dept_emp de ON e.emp_no = de.emp_no"
                            + " INNER JOIN departments d ON de.dept_no = d.dept_no"
                            + " INNER JOIN dept_manager dm ON d.dept_no = dm.dept_no"

                            + " WHERE e.emp_no = ? "
                            + " AND t.to_date = ? "
                            + " AND s.to_date = ? "
                            + " AND de.to_date = ? "
                            + "AND dm.to_date = ? ";

           try (PreparedStatement pstmt = con.prepareStatement(strSelect)) {
            // 2. Define your criteria using modern Java Date APIs
            LocalDate targetDate = LocalDate.of(9999, 1, 1);

            pstmt.setObject(1, ID);
            pstmt.setObject(2, targetDate);
            pstmt.setObject(3, targetDate);
            pstmt.setObject(4, targetDate);
            pstmt.setObject(5, targetDate);


            // Execute SQL statement
            ResultSet rset = pstmt.executeQuery();
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employee_id");
                emp.first_name = rset.getString("employee_first_name");
                emp.last_name = rset.getString("employee_last_name");
                emp.title = rset.getString("t.title");
                emp.salary = rset.getInt("s.salary");
                emp.dept_name = rset.getString("d.dept_name");
                emp.manager = rset.getString("manager_employee_id");


                return emp;
            } else
                return null;
        }
    }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get Employee
        Employee emp = a.getEmployee(255530);
        // Display results
        a.displayEmployee(emp);

        // Disconnect from database
        a.disconnect();


    }
}