package com.success.project.kindacoffee.services.people;

import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.entities.people.User;
import com.success.project.kindacoffee.services.CrudAbstractService;

import java.util.Optional;

public interface EmployeeService extends CrudAbstractService<Employee, Integer> {

    Optional<Employee> findByUser(User user);

    Optional<Employee> findByUsername(String username);
}
