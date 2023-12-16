package com.success.project.kindacoffee.util.frontend.wrappers;

import com.success.project.kindacoffee.util.frontend.forms.ManufacturingProcessForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@Validated
public class ProductForm {

    private Integer productId;

    @NotEmpty(message = "Can't be empty")
    private String productName;

    private String username;

    @Valid
    @NotEmpty
    @NotNull
    private List<ManufacturingProcessForm> manufacturingProcessFormList;
}
