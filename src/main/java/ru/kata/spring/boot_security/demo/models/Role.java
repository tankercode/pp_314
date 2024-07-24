package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_id", nullable = false)
    private int id;

    @Column
    private String type;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return getType();
    }
}
