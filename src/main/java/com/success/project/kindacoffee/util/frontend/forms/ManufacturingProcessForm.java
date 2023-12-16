package com.success.project.kindacoffee.util.frontend.forms;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ManufacturingProcessForm {

    private String id;

    private Integer productId;

    private Integer actionId;

    private Integer robotId;

    private String employeeName;

    @Pattern(regexp = "([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])", message = "Use pattern hh:mm:ss")
    private String workingTime;

    public ManufacturingProcessForm() {
    }
}
