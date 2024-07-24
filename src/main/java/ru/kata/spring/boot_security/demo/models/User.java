package ru.kata.spring.boot_security.demo.models;

/*import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;*/
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    /*@NotEmpty(message = "Field the Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100")*/
    private String name;

    @Column
    /*@NotEmpty(message = "Field the LastName cannot be empty")
    @Size(min = 2, max = 100, message = "LastName length must be between 2 and 100")*/
    private String lastname;

    @Column
    /*@NotEmpty(message = "Field the Email cannot be empty")
    @Size(min = 2, max = 100, message = "Email length must be between 2 and 100")*/
    private String email;

    @Column(name = "password")
    /*@NotEmpty(message = "Field the Password cannot be empty")*/
    private String pass;

    @Column
    /*@NotEmpty(message = "Field the Age cannot be empty")
    @Min(value = 0, message = "Come on, what are u doing here?")
    @Max(value = 150, message = "nonsense, cant believe this")*/
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "User_id"),
                inverseJoinColumns = @JoinColumn(name = "Role_id"))
    private List<Role> role;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
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
