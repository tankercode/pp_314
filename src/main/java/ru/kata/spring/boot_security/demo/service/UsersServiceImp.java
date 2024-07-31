package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersServiceImp implements UserDetailsService, UserService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findOne(int id) {
        return usersRepository.findById(id);
    }

    @Override
    public void save(User user) {

        if (user.getRole() == null) {
            if (roleRepository.findByType("ROLE_USER").isPresent()) {
                user.setRole(List.of(roleRepository.findByType("ROLE_USER").get()));
                usersRepository.save(user);
            } else {
                Role role_default = new Role();
                role_default.setType("ROLE_USER");
                save(role_default);
            }
        } else {
            usersRepository.save(user);
        }

    }

    @Override
    public void save(Role role) {
        if (roleRepository.findByType(role.getType()).isEmpty()) {
            roleRepository.save(role);
        }
    }

    @Override
    public void update(int id, User tmp) {
        tmp.setId(id);
        usersRepository.save(tmp);
    }

    @Override
    public void deleteUserById(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
    }


}
