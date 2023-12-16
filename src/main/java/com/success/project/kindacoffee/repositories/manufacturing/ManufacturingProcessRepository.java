package com.success.project.kindacoffee.repositories.manufacturing;

import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturingProcessRepository extends JpaRepository<ManufacturingProcess, String> {

    Optional<ManufacturingProcess> findByProductIdAndActionIdAndRobotId(Integer productId, Integer robotId, Integer action);

}
