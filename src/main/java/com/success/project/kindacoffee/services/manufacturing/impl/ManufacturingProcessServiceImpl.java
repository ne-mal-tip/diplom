package com.success.project.kindacoffee.services.manufacturing.impl;

import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.manufacturing.ManufacturingProcessRepository;
import com.success.project.kindacoffee.services.manufacturing.ManufacturingProcessService;
import com.success.project.kindacoffee.util.frontend.forms.ManufacturingProcessForm;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManufacturingProcessServiceImpl implements ManufacturingProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturingProcessServiceImpl.class);

    private final ManufacturingProcessRepository repository;

    public ManufacturingProcessServiceImpl(ManufacturingProcessRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ManufacturingProcess save(ManufacturingProcess manufacturingProcess) {
        try {
            Optional<ManufacturingProcess> existingManufacturingProcess = findByFieldsIds(manufacturingProcess.getProduct()
                    .getId(), manufacturingProcess.getRobot()
                    .getId(), manufacturingProcess.getAction()
                    .getId());
            if (existingManufacturingProcess.isPresent()) {
                logger.info("ManufacturingProcess {}\n updated to {}\n", existingManufacturingProcess.get(), manufacturingProcess);
                ManufacturingProcess updatedProcess = existingManufacturingProcess.get();
                updatedProcess.setWorkingTime(manufacturingProcess.getWorkingTime());
                return repository.save(updatedProcess);
            } else {
                logger.info("ManufacturingProcess {} saved", manufacturingProcess);
                manufacturingProcess.setId(getCombinedId(manufacturingProcess));
                return repository.save(manufacturingProcess);
            }
        } catch (Exception e) {
            logger.error("Unable to save process {} ", manufacturingProcess, e);
            throw new RuntimeException("Unable to save ManufacturingProcess", e);
        }
    }

    public String getCombinedId(ManufacturingProcess manufacturingProcess) {
        if (manufacturingProcess.getProduct() != null && manufacturingProcess.getRobot() != null && manufacturingProcess.getAction() != null) {
            return "Robot_" + manufacturingProcess.getRobot()
                    .getId() + "_Action_" + manufacturingProcess.getAction()
                    .getId() + "_Product_" + manufacturingProcess.getProduct()
                    .getId();
        }
        return null;
    }

    @Transactional
    public List<ManufacturingProcess> saveAll(List<ManufacturingProcess> manufacturingProcessList) {
        logger.info("Started saving Manufacturing process list of {} elements", manufacturingProcessList.size());
        List<ManufacturingProcess> savedManufacturingProcessList = manufacturingProcessList.stream()
                .map(processStep -> {
                    if (processStep.getId()
                            .equals("-1")) {
                        try {
                            String id = getCombinedId(processStep);
                            Optional<ManufacturingProcess> processOptional = find(id);
                            if (processOptional.isPresent()) {
                                if (delete(id)) {
                                    logger.info("Deleting process with id {} completed successfully", id);
                                } else {
                                    throw new EntityDoesntExistException(id, ManufacturingProcess.class, "illegal identifier provided to saveAll");
                                }
                            } return new ManufacturingProcess();
                        } catch (EntityDoesntExistException e) {
                            logger.warn("Tried to delete ManufacturingProcess with id {}", getCombinedId(processStep));
                            return new ManufacturingProcess();
                        }
                    } else {
                        return save(processStep);
                    }
                })
                .filter(manufacturingProcess -> manufacturingProcess.getId() != null)
                .collect(Collectors.toList());
        logger.debug("saved Manufacturing process list of {} elements", savedManufacturingProcessList.size());
        return savedManufacturingProcessList;
    }

    public Optional<ManufacturingProcess> find(String id) {
        logSearchInfo("id", id);
        return repository.findById(id);
    }

    public Optional<ManufacturingProcess> findByFieldsIds(ManufacturingProcessForm manufacturingProcessForm) {
        logSearchInfo("robot, action and product", manufacturingProcessForm.getRobotId(), manufacturingProcessForm.getActionId(), manufacturingProcessForm.getProductId());
        return repository.findByProductIdAndActionIdAndRobotId(manufacturingProcessForm.getProductId(), manufacturingProcessForm.getRobotId(), manufacturingProcessForm.getActionId());
    }

    public Optional<ManufacturingProcess> findByFieldsIds(Integer productId, Integer robotId, Integer actionId) {
        logSearchInfo("robot, action and product", robotId, actionId, productId);
        return repository.findByProductIdAndActionIdAndRobotId(productId, robotId, actionId);
    }

    @Override
    public List<ManufacturingProcess> findAll() {
        logger.debug("Searching all ManufacturingProcess");
        List<ManufacturingProcess> list = repository.findAll();
        logger.info("ManufacturingProcess list was found");
        return list;
    }

    @Transactional
    public boolean delete(String id) {
        logSearchInfo("id", id);
        return find(id).map(manufacturingProcess -> {
                    repository.deleteById(id);
                    logger.info("ManufacturingProcess with id={} was deleted", id);
                    return true;
                })
                .orElseThrow(() -> new EntityDoesntExistException(id, ManufacturingProcess.class, "Error deleting ManufacturingProcess with id=" + id));
    }

    private void logSearchInfo(String key, Object... value) {
        logger.info("Searching ManufacturingProcess with {} {}", key, value);
    }
}
