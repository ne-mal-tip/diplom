package com.success.project.kindacoffee.services.manufacturing;

import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import com.success.project.kindacoffee.services.CrudAbstractService;

import java.util.List;

public interface ManufacturingProcessService extends CrudAbstractService<ManufacturingProcess, String> {
    List<ManufacturingProcess> saveAll(List<ManufacturingProcess> manufacturingProcessList);
}
