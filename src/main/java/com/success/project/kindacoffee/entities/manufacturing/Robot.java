package com.success.project.kindacoffee.entities.manufacturing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "ROBOT")
@ToString(exclude = {"manufacturingProcessList","bio"})

public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "POWER")
    private String power;

    @Column(name = "DRILL")
    private String drill;

    @Column(name = "TABLE_NUMBER")
    private String tableNumber;

    @Column(name = "HEIGHT")
    private String height;

    @Column(name = "TILT")
    private String tilt;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "WEIGHT")
    private String weight;

    @Column(name = "BIO")
    private String bio;

    @OneToMany(mappedBy = "robot", fetch = FetchType.LAZY)
    private List<ManufacturingProcess> manufacturingProcessList;
}
