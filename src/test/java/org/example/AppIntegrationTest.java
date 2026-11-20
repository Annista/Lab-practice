package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }

    @Test
    void testGetEmployee()
    {
        Employee emp = app.getEmployee(255530);
        assertEquals(emp.emp_no, 255530);
        assertEquals(emp.first_name, "Ronghao");
        assertEquals(emp.last_name, "Garigliano");
    }

    @Test
    void testGetDepartment()
    {
        Department dept= app.getDepartment("Sales");
        assertEquals(dept.dept_no, "d007");
        assertEquals(dept.dept_name, "Sales");
        assertEquals(dept.manager.emp_no, 111133);
    }

  @Test
    void testGetEmployeeIsNull()
  {
      Employee emp = app.getEmployee(-1);
      assertNull(emp);
  }

    @Test
    void testGetDepartmentIsNull()
    {
        Department dept= app.getDepartment("Crime");
        assertNull(dept);
    }

   @Test
    void testAddEmployee()
    {
        Employee emp = new Employee();
        emp.emp_no = 500005;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        app.addEmployee(emp);
        emp = app.getEmployee(500005);
        assertEquals(emp.emp_no, 500005);
        assertEquals(emp.first_name, "Kevin");
        assertEquals(emp.last_name, "Chalmers");
    }

    @Test
    void testGetAllSalaries(){
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees = app.getAllSalaries();
        assertNotNull(employees, "Salary Report should not be null!!!");
}

  @Test
   void testGetSalariesByDepartment(){
        Department sales_dept= app.getDepartment("Sales");
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees= app.getSalariesByDepartment(sales_dept);
        assertNotNull(employees, "Department Salary Report should not be null!!!");

  }

}