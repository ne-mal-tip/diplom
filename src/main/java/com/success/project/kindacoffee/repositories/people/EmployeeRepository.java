package com.success.project.kindacoffee.repositories.people;

import com.success.project.kindacoffee.entities.business.Business;
import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.entities.people.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findEmployeeByUser(User user);

    Optional<Employee> findEmployeeByUserUsername(String name);

    List<Employee> findEmployeesByBusiness(Business business);

}
