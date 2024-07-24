package ru.kata.spring.boot_security.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public Optional<User> findOne(int id){
        return usersRepository.findById(id);
    }

    public void save(User user) throws RoleNotFoundException {

        if (roleRepository.findByType("ROLE_USER").isPresent()) {
            user.setRole(List.of(roleRepository.findByType("ROLE_USER").get()));
            usersRepository.save(user);
        } else {
            throw new RoleNotFoundException(String.format("Роль '%s' не найдена", "ROLE_USER"));
        }
    }

    public void update(int id, User tmp) {
        tmp.setId(id);
        usersRepository.save(tmp);
    }

    public void deleteUserById(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository.findByName(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
    }


}
