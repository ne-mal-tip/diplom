package com.success.project.kindacoffee.services.people;

import com.success.project.kindacoffee.entities.people.User;
import com.success.project.kindacoffee.services.CrudAbstractService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, CrudAbstractService<User, Integer> {
    Optional<User> findUserByUsername(String username);

}
