package com.success.project.kindacoffee.entities.enums;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
