package com.success.project.kindacoffee.entities.business;

import com.success.project.kindacoffee.entities.people.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "BUSINESSES")
@ToString(exclude = "employees")
public class Business {

    @Id
    @Column(name = "ID")
    private int Id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    private List<Employee> employees;


}
