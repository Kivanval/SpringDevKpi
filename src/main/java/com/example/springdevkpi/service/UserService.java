package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.User;
import com.example.springdevkpi.web.dto.Credentials;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@Slf4j
public class UserService {


    @Delegate
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean signUp(Credentials credentials) {
        var optUserRole = roleRepository.findByName("USER");
        if (optUserRole.isEmpty()) {
            log.warn("Role USER don't exist. Registration have been cancelled");
            return false;
        }
        var user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        var userRole = optUserRole.get();
        user.setRole(userRole);
        userRepository.save(user);
        return true;
    }

    public User map(Credentials credentials) {
        var user = new User();
        user.setPassword(credentials.getPassword());
        return user;
    }

    public Optional<User> findByCredentials(Credentials credentials) {
        var optUser = userRepository.findByUsername(credentials.getUsername());
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
                return optUser;
            }
        }
        return Optional.empty();
    }


}
