package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Users_table")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private int id;

    private String name;

    private String lastname;

    private String email;

    @Column(name = "password")
    private String pass;

    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles_table",
            joinColumns = @JoinColumn(name = "User_id"),
            inverseJoinColumns = @JoinColumn(name = "Role_id"))
    private List<Role> role;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    public void setPass(String pass) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.pass = bCryptPasswordEncoder.encode(new StringBuilder(pass));
    }

    @Override
    public String getPassword() {
        return getPass();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
