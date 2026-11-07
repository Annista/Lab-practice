package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */

    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
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

                    Department dept_emp = new Department();
                    dept_emp.dept_name = rset.getString("d.dept_name");
                    emp.dept_name = dept_emp;

                    Employee mang_emp = new Employee();
                    mang_emp.emp_no = rset.getInt("manager_employee_id");
                    emp.manager = mang_emp;


                    return emp;
                } else
                    return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }


    public void getSalaryRoleReport(String title) {

        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =

                    " SELECT e.emp_no AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, s.salary"
                            + " FROM employees e, salaries s, titles"

                            + " WHERE e.emp_no = s.emp_no"
                            + " AND e.emp_no = titles.emp_no"
                            + " AND s.to_date = ? "
                            + " AND titles.to_date = ? "
                            + " AND titles.title = ? "
                            + " ORDER BY e.emp_no ASC";


            try (PreparedStatement pstmt = con.prepareStatement(strSelect)) {
                // Define your criteria using modern Java Date APIs
                LocalDate targetDate = LocalDate.of(9999, 1, 1);

                pstmt.setObject(1, targetDate);
                pstmt.setObject(2, targetDate);
                pstmt.setObject(3, title);

                // Execute SQL statement
                ResultSet rset = pstmt.executeQuery();


                // Return new employee
                Employee emp = new Employee();
                // While there is a valid row, employee
                //record will be retrieved and displayed
                while (rset.next()) {
                    emp.emp_no = rset.getInt("employee_id");
                    emp.first_name = rset.getString("employee_first_name");
                    emp.last_name = rset.getString("employee_last_name");
                    emp.salary = rset.getInt("s.salary");

                    //return emp;

                    displaySalaryRecord(emp);

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            //return null;

        }

    }


    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    " SELECT e.emp_no AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, s.salary "
                            + " FROM employees e, salaries s "
                            + " WHERE e.emp_no = s.emp_no AND s.to_date = ? "
                            + " ORDER BY e.emp_no ASC";

            try (PreparedStatement pstmt = con.prepareStatement(strSelect)) {
                // Define your criteria using modern Java Date APIs
                LocalDate targetDate = LocalDate.of(9999, 1, 1);

                pstmt.setObject(1, targetDate);

                // Execute SQL statement
                ResultSet rset = pstmt.executeQuery();

                // Return new employee
                ArrayList<Employee> employees = new ArrayList<Employee>();
                // While there is a valid row, employee
                //record will be retrieved and displayed
                while (rset.next()) {
                    Employee emp = new Employee();
                    emp.emp_no = rset.getInt("employee_id");
                    emp.first_name = rset.getString("employee_first_name");
                    emp.last_name = rset.getString("employee_last_name");
                    emp.salary = rset.getInt("s.salary");
                    employees.add(emp);


                }
                return employees;
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void displayEmployee(Employee emp) {
        if (emp != null && emp.dept_name != null) {

            //Create employee object for manager to obtain manager information
           /* Employee emp_mang= getEmployee(emp.manager.emp_no);*/
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name.dept_name + "\n"  //Rename emp.dept_name since it will be confused with dept_name.dept_name
                           /* + "Manager ID: " + emp_mang.emp_no +"\n"
                            + "Manager Name: " + emp_mang.first_name +"\t" + emp_mang.last_name +"\n"*/
                            +"Manager: " + emp.manager.emp_no +"\n");



        }
    }

    public void displaySalaryRecord(Employee emp) {

        if (emp != null) {
            System.out.println(emp.emp_no + "\t" + emp.first_name + "\t" + emp.last_name + "\t" + emp.salary + "\n");
        }
    }

    /**
     * Prints a list of employees.
     *
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees) {

        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }

        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees) {
            if (emp == null)
                continue;

            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }


   public Department getDepartment(String dept_name) {

        try{
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    " SELECT d.dept_no AS depart_num, d.dept_name AS depart_name, dm.emp_no AS mang_num"
                    + " FROM departments d, dept_manager dm "
                    + " WHERE d.dept_no = dm.dept_no"
                    + " AND d.dept_name = ? ";

            try( PreparedStatement pstmt = con.prepareStatement(strSelect)){
                pstmt.setObject(1, dept_name);

                ResultSet rset = pstmt.executeQuery();
                if (rset.next()){

                    Department deptmt= new Department();
                    deptmt.dept_no= rset.getString("depart_num");
                    deptmt.dept_name= rset.getString("depart_name");
                    Employee mang_emp= new Employee();
                    mang_emp.emp_no= rset.getInt("mang_num");
                    deptmt.manager=mang_emp;

                    return deptmt;
                }else{

                    return null;
                }


            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;

        }

    }

    public void printDepartment(Department depart){
        if (depart != null){

           System.out.println("Department No.: " + depart.dept_no +"\n"
           + "Department Name: " + depart.dept_name +"\n"
           + "Department Manager No.: " + depart.manager.emp_no +"\n");
        }
    }



    public ArrayList<Employee> getSalariesByDepartment(Department dept){
        try{

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    " SELECT e.emp_no AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, s.salary"
                           + " FROM employees e, salaries s, dept_emp de, departments d"
                           + " WHERE e.emp_no = s.emp_no "
                           + " AND e.emp_no = de.emp_no "
                           + " AND de.dept_no = d.dept_no "
                           + " AND s.to_date = ? "
                           + " AND d.dept_no = ? "
                           + " ORDER BY e.emp_no ASC ";

            try (PreparedStatement pstmt = con.prepareStatement(strSelect)) {
                // Define your criteria using modern Java Date APIs
                LocalDate targetDate = LocalDate.of(9999, 1, 1);

                pstmt.setObject(1, targetDate);
                pstmt.setObject(2, dept.dept_no);

                // Execute SQL statement
                ResultSet rset = pstmt.executeQuery();

                // Return new employee
                ArrayList<Employee> employees = new ArrayList<Employee>();
                // While there is a valid row, employee
                //record will be retrieved and displayed
                while (rset.next()) {
                    Employee emp = new Employee();
                    emp.emp_no = rset.getInt("employee_id");
                    emp.first_name = rset.getString("employee_first_name");
                    emp.last_name = rset.getString("employee_last_name");
                    emp.salary = rset.getInt("s.salary");
                    employees.add(emp);

                }
                return employees;

              }

            } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }


    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        Department dept = a.getDepartment("Development");
        ArrayList<Employee> employees = a.getSalariesByDepartment(dept);


        // Print salary report
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }



}