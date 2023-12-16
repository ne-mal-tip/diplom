package com.success.project.kindacoffee.entities.manufacturing;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ACTIONS")
public class Action {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME")
    private String name;
}
