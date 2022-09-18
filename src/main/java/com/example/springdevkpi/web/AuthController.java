package com.example.springdevkpi.web;

import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.service.security.JwtProvider;
import com.example.springdevkpi.web.dto.Credentials;
import com.example.springdevkpi.web.dto.JwtToken;
import com.example.springdevkpi.web.dto.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final JwtProvider jwtProvider;


    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserPayload> signUp(@RequestBody @Valid Credentials credentials) {
        return userService.signUp(credentials) ?
                ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.internalServerError().build();
    }


    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody @Valid Credentials credentials) {
        var optUser = userService.findByCredentials(credentials);
        if (optUser.isPresent()) {
            var token = jwtProvider.generateToken(optUser.get().getUsername());
            return ResponseEntity.ok(new JwtToken(token));
        }
        return ResponseEntity.badRequest().build();
    }
}
