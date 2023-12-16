package com.success.project.kindacoffee.entities.manufacturing;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MANUFACTURING_PROCESS")
public class ManufacturingProcess {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTION_ID")
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROBOT_ID")
    private Robot robot;

    @Column(name = "WORKING_TIME")
    private String workingTime;

    public ManufacturingProcess() {
    }

    public ManufacturingProcess(String id, Product product, Action action, Robot robot, String workingTime) {
        this.id = id;
        this.product = product;
        this.action = action;
        this.robot = robot;
        this.workingTime = workingTime;
    }

}
