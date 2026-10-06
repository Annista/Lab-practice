# USE CASE: 7 Update an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want to *update an employee's details* so that *employee's details are kept up-to-date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database contains current employee salary data.

### Success End Condition

Employee details are successfully modified/updated.

### Failed End Condition

Employee details are not updated.

### Primary Actor

HR Advisor.

### Trigger

A request to update employee details is sent to HR.

## MAIN SUCCESS SCENARIO

1. A request to update employee details is sent to HR.
2. HR advisor captures ID of employee whose details need to be updated.
3. HR advisor makes the specified changes to the employee details.
4. HR advisor saves the employee details update onto the database.
5. HR receives confirmation from system that the update has been saved.

## EXTENSIONS

3. **Employee ID does not exist**:
    1. HR advisor informs relevent personnel that employee does not exist.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
