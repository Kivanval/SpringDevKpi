package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.User;
import com.example.springdevkpi.web.dto.Credentials;
import com.example.springdevkpi.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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

    public boolean signInUser(User user) {
        var optUserRole = roleRepository.findByName("USER");
        if (optUserRole.isPresent()) {
            var userRole = optUserRole.get();
            user.setRole(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return true;
        }
        log.warn("Role USER don't exist. Registration have been cancelled");
        return false;
    }

    public User fillCredentials(Credentials credentials) {
        var user = new User();
        user.setUsername(credentials.username());
        user.setPassword(credentials.password());
        return user;
    }

    public Optional<User> findByCredentials(String username, String password) {
        var optUser = userRepository.findByUsername(username);
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return optUser;
            }
        }
        return Optional.empty();
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}
