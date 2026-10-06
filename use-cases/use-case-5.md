# USE CASE: 5 Add New Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *add a new employee's details* so that *I can ensure the new employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database allows users to save data on it.

### Success End Condition

HR successfully adds new employee details to database

### Failed End Condition

HR is not able to save new employee details on database

### Primary Actor

HR Advisor.

### Trigger

A new employee is hired, and their details need to be entered into the system.

## MAIN SUCCESS SCENARIO

1. HR is requested to enter new employee information.
2. HR advisor captures employee information such as name of the employee, employee ID, department, role, and salary.
3. HR advisor enters employee information in database.
4. HR advisor receives confirmation that new employee information is saved in database.

## EXTENSIONS

3. **HR is notified by system that one or more pieces of employee information is invalid**:
    1. HR advisor compares captured employee information with its source.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
