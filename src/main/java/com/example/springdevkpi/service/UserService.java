package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.domain.User;
import com.example.springdevkpi.data.transfer.Credentials;
import com.example.springdevkpi.data.transfer.UserBasePayload;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public boolean updateUser(UserBasePayload payload, String username) {
        var optUser = userRepository.findByUsername(username);
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (payload.getUsername() != null) {
                user.setUsername(payload.getUsername());
            }
            if (payload.getPassword() != null) {
                user.setPassword(payload.getPassword());
            }
            if (payload.getRoleName() != null) {
                roleRepository.findByName(payload.getRoleName()).ifPresent(user::setRole);
            }
            return true;
        }
        return false;
    }


}