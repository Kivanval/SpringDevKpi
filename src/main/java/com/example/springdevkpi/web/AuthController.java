package com.example.springdevkpi.web;

import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        //TODO
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        var user = userService.findByCredentials(request.login(), request.password());
        //TODO
        var token = jwtProvider.generateToken(user.get().getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
