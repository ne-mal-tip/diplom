package com.success.project.kindacoffee.services.manufacturing.impl;

import com.success.project.kindacoffee.entities.manufacturing.Action;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.manufacturing.ActionRepository;
import com.success.project.kindacoffee.services.manufacturing.ActionService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private static final Logger logger = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final ActionRepository repository;

    public ActionServiceImpl(ActionRepository repository) {
        this.repository = repository;
    }

    public Action save(Action action) {
        Action saved = repository.save(action);
        logActionInfo("saved", saved);
        return saved;
    }

    public Optional<Action> find(Integer id) {
        logSearchInfo("id", id);
        return repository.findById(id);
    }

    public List<Action> findAll() {
        logger.debug("Searching all Actions");
        List<Action> list = repository.findAll();
        logger.info("Action list was found");
        return list;
    }

    public boolean delete(Integer id) {
        logSearchInfo("id", id);
        return find(id).map(action -> {
                    repository.delete(action);
                    logger.info("Action with id={} was deleted", id);
                    return true;
                })
                .orElseThrow(() -> new EntityDoesntExistException(id, Action.class, "Error deleting Action with id=" + id));
    }

    private void logSearchInfo(String key, Object value) {
        logger.debug("Searching Action with {} {}", key, value);
    }

    private void logActionInfo(String action, Action actionEntity) {
        logger.info("Action {} {} with id {}", action, actionEntity.getName(), actionEntity.getId());
    }
}
