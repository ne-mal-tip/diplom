package com.success.project.kindacoffee.entities.manufacturing;

import com.success.project.kindacoffee.entities.people.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS")
@ToString(exclude = "manufacturingProcessList")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "PRODUCT_EMPLOYEES", joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private Employee employee;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ManufacturingProcess> manufacturingProcessList;

    public Product() {
    }

    public Product(String name) {
        this.setName(name);
    }

}
