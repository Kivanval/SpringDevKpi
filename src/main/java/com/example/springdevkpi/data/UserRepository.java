package com.example.springdevkpi.data;

import com.example.springdevkpi.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findUserByEmail(String email);
}
