package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registryUser(User user) {
        var optionalUserRole = roleRepository.findByName("USER");
        if (optionalUserRole.isPresent()) {
            var userRole = optionalUserRole.get();
            user.setRole(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return user;
        }
        throw new IllegalArgumentException();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByCredentials(String username, String password) {
        var optionalUser = findByUsername(username);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return optionalUser;
            }
        }
        return Optional.empty();
    }
}
