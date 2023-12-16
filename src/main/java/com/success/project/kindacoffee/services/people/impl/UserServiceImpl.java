package com.success.project.kindacoffee.services.people.impl;

import com.success.project.kindacoffee.entities.people.User;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.people.UserRepository;
import com.success.project.kindacoffee.services.people.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    private final UserRepository repository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(PasswordEncoder bCryptPasswordEncoder, UserRepository repository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.repository = repository;
    }

    @Transactional
    public User save(User user) {
        Optional<User> founded = findUserByUsername(user.getUsername());
        if (founded.isPresent()) {
            User saved = repository.save(user);
            logger.info("User {} saved", saved);
            return saved;
        } else {
            User registered = register(user);
            logger.info("User {} registered", user);
            return registered;
        }
    }

    public User register(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return repository.save(user);
    }

    @Transactional
    public Optional<User> find(Integer id) {
        Optional<User> founded;
        logger.debug("Searching user with id {}", id);
        founded = repository.findById(id);
        if (founded.isPresent()) {
            logger.info("User with id {} was found successfully", id);
        } else {
            logger.warn("User with id {} doesnt exist", id);
        }
        return founded;
    }

    @Transactional
    public List<User> findAll() {
        logger.debug("Searching all users");
        List<User> list = repository.findAll();
        logger.info("User list was found, it contains {} elements", list.size());
        return list;
    }

    @Transactional
    public boolean delete(Integer id) {
        logger.debug("Deleting user with id {}", id);
        try {
            repository.deleteById(id);
            logger.info("User with id={} was deleted", id);
            return true;
        } catch (Exception e) {
            logger.warn(e.toString());
            throw new EntityDoesntExistException(id, User.class, "User with id={} doesn't axist");
        }
    }


    @Transactional
    public Optional<User> findUserByUsername(String username) {
        logger.info("findUserByUsername {} ", username);
        logger.debug("Searching for user with username={}", username);
        try {
            Optional<User> user = repository.findByUsername(username);
            logger.info("User with username={} was founded", username);
            return user;
        } catch (Exception e) {
            logger.warn(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (logger.isInfoEnabled()) {
            logger.info("Searching for user with username={} after user loading", username);
        }
        if (user.isEmpty()) {
            logger.warn("Login of user \"{}\" failed, this user doesnt exist", username);
            throw new UsernameNotFoundException(username);
        }
        logger.info("Found successfully user with username \"{}\" ", username);
        return user.get();
    }
}
