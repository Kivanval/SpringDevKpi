package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.User;
import com.example.springdevkpi.exception.RoleNotDefException;
import com.example.springdevkpi.web.data.transfer.Credentials;
import com.example.springdevkpi.web.data.transfer.UserUpdatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean signUp(Credentials credentials, String roleName) {
        var optUserRole = roleRepository.findByName(roleName);
        if (optUserRole.isEmpty()) {
            log.warn("Role {} don't exist. Registration have been cancelled", roleName);
            throw new RoleNotDefException();
        }
        if (userRepository.existsByUsername(credentials.getUsername())) return false;
        var user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        var userRole = optUserRole.get();
        user.setRole(userRole);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean signUp(Credentials credentials) {
        return signUp(credentials, DefaultRoles.USER);
    }

    @Transactional
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

    @Transactional
    public boolean update(UserUpdatePayload payload, String username) {
        var optUser = userRepository.findByUsername(username);
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (payload.getUsername() != null) {
                user.setUsername(payload.getUsername());
            }
            if (payload.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(payload.getPassword()));
            }
            if (payload.getRoleName() != null) {
                roleRepository.findByName(payload.getRoleName()).
                        ifPresent(user::setRole);
            }
            userRepository.save(user);
            return true;
        }
        log.warn("User by username {} doesn't exists", username);
        return false;
    }


    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    public void deleteByUsername(String username) {
        if (userRepository.existsByUsername(username))
            userRepository.deleteByUsername(username);
    }

    @Transactional
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
