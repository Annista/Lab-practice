# USE CASE: 8 Delete an Employee's Details 

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database allows user to delete employee details.

### Success End Condition

Employee Details is successfully deleted.

### Failed End Condition

Employee details is not deleted.

### Primary Actor

HR Advisor.

### Trigger

A request for employee detail deletion is sent to HR.

## MAIN SUCCESS SCENARIO

1. HR receives request to delete employee details
2. HR advisor captures employee ID to get access to employee's details.
3. HR advisor deletes employee details.
4. HR advisor receives confirmation that employee details has been deleted.

## EXTENSIONS

3. **Employee ID does not exist**:
    1. HR advisor informs relevent personnel that the employee does not exist.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
