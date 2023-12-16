package com.success.project.kindacoffee.entities.people;

import com.success.project.kindacoffee.entities.business.Business;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @Column(name = "ID")
    int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BUSINESSES_EMPLOYEES",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "BUSINESS_ID")
    )
    private Business business;

}


