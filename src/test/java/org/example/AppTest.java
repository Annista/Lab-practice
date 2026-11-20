package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printSalariesTestNull()
    {
        app.printSalaries(null);
    }

    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        app.printSalaries(employess);
    }

    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        employess.add(null);
        app.printSalaries(employess);
    }

    @Test
    void printSalaries()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalaries(employees);
    }
    @Test
    void displayEmployeeTestNull(){
        app.displayEmployee(null);
}

    @Test
    void displayEmployeeTestEmpty(){
        Employee emp= new Employee();
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestFirstNameNull(){
        Employee emp= new Employee();
        emp.first_name=null;
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestLastNameNull(){
        Employee emp= new Employee();
        emp.last_name=null;
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestDeptNull(){
        Employee emp= new Employee();
        emp.dept_name=null;
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestManagerNull(){
        Employee emp= new Employee();
        emp.manager=null;
        app.displayEmployee(emp);
    }


   @Test
    void displayEmployee(){
        Employee emp= new Employee();
        emp.emp_no= 1001;
        emp.first_name= "Steph";
        emp.last_name= "Foster";
        emp.title= "Developer";
        emp.salary=18900;

        Department dept= new Department();
        dept.dept_name="Development";
        emp.dept_name=dept;

       Employee mang_emp= new Employee();
       mang_emp.emp_no=2001;
       emp.manager=mang_emp;


        app.displayEmployee(emp);
    }
}
