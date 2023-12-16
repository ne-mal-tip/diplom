package com.success.project.kindacoffee.util.frontend.utils;

import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import com.success.project.kindacoffee.entities.manufacturing.builders.ManufacturingProcessBuilder;
import com.success.project.kindacoffee.services.manufacturing.ActionService;
import com.success.project.kindacoffee.services.manufacturing.ProductService;
import com.success.project.kindacoffee.services.manufacturing.RobotService;
import com.success.project.kindacoffee.util.frontend.forms.ManufacturingProcessForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ManufacturingProcessFormUtil implements AbstractUnwrapUtil<ManufacturingProcess, ManufacturingProcessForm> {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturingProcessFormUtil.class);

    private final ProductService productService;
    private final ActionService actionService;
    private final RobotService robotService;

    public ManufacturingProcessFormUtil(ProductService productService, ActionService actionService, RobotService robotService) {
        this.productService = productService;
        this.actionService = actionService;
        this.robotService = robotService;
    }

    static public List<ManufacturingProcessForm> wrap(List<ManufacturingProcess> manufacturingProcesses) {
        return manufacturingProcesses.stream()
                .map(ManufacturingProcessFormUtil::wrapSingle)
                .collect(Collectors.toList());
    }

    public static ManufacturingProcessForm wrapSingle(ManufacturingProcess process) {
        if (Objects.isNull(process.getId())) {
            return new ManufacturingProcessForm();
        }
        ManufacturingProcessForm form = new ManufacturingProcessForm();
        form.setId(process.getId());
        form.setProductId(process.getProduct()
                .getId());
        form.setEmployeeName(process.getProduct()
                .getEmployee()
                .getUser()
                .getUsername());
        form.setActionId(process.getAction()
                .getId());
        form.setRobotId(process.getRobot()
                .getId());
        form.setWorkingTime(process.getWorkingTime());
        return form;
    }

    @Override
    public ManufacturingProcess unwrapSingle(ManufacturingProcessForm manufacturingProcessForm) {
        logger.debug("Unwrapping form {}", manufacturingProcessForm);
        return new ManufacturingProcessBuilder().with(process -> {
                    process.setId(manufacturingProcessForm.getId());
                    process.setProduct(manufacturingProcessForm.getProductId() != null ? productService.find(manufacturingProcessForm.getProductId())
                            .get() : null);
                    process.setAction(manufacturingProcessForm.getActionId() != null ? actionService.find(manufacturingProcessForm.getActionId())
                            .get() : null);
                    process.setRobot(manufacturingProcessForm.getRobotId() != null ? robotService.find(manufacturingProcessForm.getRobotId())
                            .get() : null);
                    process.setWorkingTime(manufacturingProcessForm.getWorkingTime());
                })
                .createProcess();
    }


    @Override
    public List<ManufacturingProcess> unwrap(List<ManufacturingProcessForm> forms) {
        logger.info("Un-wrapping list of {} forms", forms.size());
        return forms.stream()
                .map(this::unwrapSingle)
                .collect(Collectors.toList());
    }

    public List<ManufacturingProcess> unwrapForProduct(List<ManufacturingProcessForm> forms, Integer productId) {
        forms.forEach(form -> form.setProductId(productId));
        logger.debug("Un-wrapping list of {} forms", forms.size());
        return forms.stream()
                .map(this::unwrapSingle)
                .collect(Collectors.toList());
    }
}
