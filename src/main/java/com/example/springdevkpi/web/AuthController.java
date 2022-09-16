package com.example.springdevkpi.web;

import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.service.security.JwtProvider;
import com.example.springdevkpi.web.dto.JwtToken;
import com.example.springdevkpi.web.dto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid Credentials credentials) {
        return userService.signInUser(userService.fillCredentials(credentials)) ?
                ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    @PostMapping("/auth")
    public ResponseEntity<JwtToken> auth(@RequestBody @Valid Credentials credentials) {
        var optUser = userService.findByCredentials(credentials.username(), credentials.password());
        if (optUser.isPresent()) {
            var token = jwtProvider.generateToken(optUser.get().getUsername());
            return ResponseEntity.ok(new JwtToken(token));
        }
        return ResponseEntity.badRequest().build();
    }
}
