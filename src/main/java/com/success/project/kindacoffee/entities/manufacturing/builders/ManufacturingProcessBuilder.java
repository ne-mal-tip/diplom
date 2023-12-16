package com.success.project.kindacoffee.entities.manufacturing.builders;

import com.success.project.kindacoffee.entities.manufacturing.Action;
import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.entities.manufacturing.Robot;
import lombok.Data;

import java.util.function.Consumer;

@Data
public class ManufacturingProcessBuilder {

    private String id;

    private Product product;

    private Action action;

    private Robot robot;

    private String workingTime;

    public ManufacturingProcessBuilder with(Consumer<ManufacturingProcessBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }


    public ManufacturingProcess createProcess() {
        return new ManufacturingProcess(id, product, action, robot, workingTime);
    }
}