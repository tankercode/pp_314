package ru.kata.spring.boot_security.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DbInit {

    private final UsersServiceImp usersServiceImp;

    @Bean
    public void doInit() {

        Role admin_role = new Role();
        admin_role.setType("ROLE_ADMIN");
        usersServiceImp.save(admin_role);

        Role user_role = new Role();
        user_role.setType("ROLE_USER");
        usersServiceImp.save(user_role);

        User user = new User();
        user.setAge(10);
        user.setPassword("password");
        user.setName("user");
        user.setLastname("lastname");
        user.setEmail("mail@mail.ru");
        usersServiceImp.save(user);

        User user1 = new User();
        user1.setAge(11);
        user1.setPassword("password");
        user1.setName("admin");
        user1.setLastname("lastname1");
        user1.setEmail("mail@mail1.ru");
        user1.setRole(List.of(admin_role));
        usersServiceImp.save(user1);
    }
}
