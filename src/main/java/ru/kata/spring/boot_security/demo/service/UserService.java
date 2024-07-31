package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findOne(int id);

    void save(User user) throws RoleNotFoundException;

    void save(Role role);

    void update(int id, User tmp);

    void deleteUserById(int id);
}
