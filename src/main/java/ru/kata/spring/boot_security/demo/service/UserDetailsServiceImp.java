package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
    }
}
