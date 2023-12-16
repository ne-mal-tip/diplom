package com.success.project.kindacoffee.services.manufacturing.impl;

import com.success.project.kindacoffee.entities.manufacturing.Robot;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.manufacturing.RobotRepository;
import com.success.project.kindacoffee.services.manufacturing.RobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RobotServiceImpl implements RobotService {

    private static final Logger logger = LoggerFactory.getLogger(RobotServiceImpl.class);

    private final RobotRepository repository;

    public RobotServiceImpl(RobotRepository repository) {
        this.repository = repository;
    }

    public Robot save(Robot robot) {
        Robot saved = repository.save(robot);
        logger.info("Robot {} saved", saved);
        return saved;
    }

    public Optional<Robot> find(Integer id) {
        logger.debug("Searching robot with id {}", id);
        Optional<Robot> found = repository.findById(id);
        found.ifPresentOrElse(robot -> logger.info("Robot {} with id {} was found successfully", robot.getModel(), robot.getId()), () -> logger.info("Robot with id {} was not found", id));
        return found;
    }

    public List<Robot> findAll() {
        logger.debug("Searching all robots");
        List<Robot> list = repository.findAll();
        logger.info("Robot list was found");
        return list;
    }

    public boolean delete(Integer id) {
        logger.debug("Deleting robot with id {}", id);
        try {
            repository.deleteById(id);
            logger.info("Robot with id={} was deleted", id);
            return true;
        } catch (Exception e) {
            logger.warn("Error deleting robot with id={}", id, e);
            throw new EntityDoesntExistException(id, Robot.class, "Error deleting robot with id=" + id);
        }
    }
}

