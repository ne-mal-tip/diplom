package com.success.project.kindacoffee.repositories.people;

import com.success.project.kindacoffee.entities.people.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    // public Optional<Employee> findEmployeeByUsername(String username);

}