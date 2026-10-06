# USE CASE: 6 View an Employee's Details 

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *view an employee's details * so that *the employee's promotion request can be supported.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  System is able to display/output employee details.

### Success End Condition

The employee's details are successfully outputted by system.

### Failed End Condition

System does not display employee's details.

### Primary Actor

HR Advisor.

### Trigger

A request for employee's promotion is sent to HR.

## MAIN SUCCESS SCENARIO

1. Request for employee's promotion is sent to HR.
2. HR advisor captures name and ID of employee to get employee information.
3. HR advisor extracts the employee details.
4. HR advisor provides employee details to the relevent personnel.

## EXTENSIONS

3. **Employee Name and/or ID doesnot exist**:
    1. HR advisor informs relevent personnel that this employee doesnot exist.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
