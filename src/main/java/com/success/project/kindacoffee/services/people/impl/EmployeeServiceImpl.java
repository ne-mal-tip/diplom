package com.success.project.kindacoffee.services.people.impl;

import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.entities.people.User;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.people.EmployeeRepository;
import com.success.project.kindacoffee.services.people.EmployeeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Employee save(Employee employee) {
        try {
            Employee saved = repository.save(employee);
            logger.info("Employee with ID={} saved", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving employee: {}", employee, e);
            throw new RuntimeException("Error saving employee: " + employee, e);
        }
    }

    public Optional<Employee> find(Integer id) {
        logger.debug("Searching employee with ID {}", id);
        Optional<Employee> founded = repository.findById(id);
        founded.ifPresentOrElse(employee -> logger.info("Employee with ID {} was found successfully", employee.getId()), () -> logger.info("Employee with ID {} was not found", id));
        return founded;
    }

    @Override
    public Optional<Employee> findByUser(User user) {
        logger.debug("Searching employee with user {}", user);
        Optional<Employee> founded = repository.findEmployeeByUser(user);
        founded.ifPresentOrElse(employee -> logger.info("Employee with account {} was found successfully", employee.getId()), () -> logger.info("Employee with account {} was not found", user.getUsername()));
        return founded;
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        logger.debug("Searching employee with username {}", username);
        Optional<Employee> founded = repository.findEmployeeByUserUsername(username);
        founded.ifPresentOrElse(employee -> logger.info("Employee with account {} was found successfully", employee.getId()), () -> logger.info("Employee with account {} was not found", username));
        return founded;
    }

    public List<Employee> findAll() {
        logger.debug("Searching all employees");
        List<Employee> list = repository.findAll();
        logger.info("Employee list was found");
        return list;
    }

    public boolean delete(Integer id) {
        logger.debug("Deleting employee with ID {}", id);
        try {
            repository.deleteById(id);
            logger.info("Employee with ID={} was deleted", id);
            return true;
        } catch (Exception e) {
            logger.warn("Error deleting employee with ID={}", id, e);
            throw new EntityDoesntExistException(id, Employee.class, "Error deleting employee with ID=" + id);
        }
    }

}
