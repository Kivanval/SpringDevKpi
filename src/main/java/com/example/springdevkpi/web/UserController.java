package com.example.springdevkpi.web;

import com.example.springdevkpi.data.transfer.*;
import com.example.springdevkpi.security.JwtProvider;
import com.example.springdevkpi.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    private final ModelMapper modelMapper;

    private final JwtProvider jwtProvider;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, JwtProvider jwtProvider) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtProvider = jwtProvider;
    }

    private static final String USER_PROPERTIES = "id|username|createdAt|role_id";

    @GetMapping("/")
    public Collection<UserPayload> getUsers(
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "id") @Pattern(regexp = USER_PROPERTIES) final String sortBy) {
        return userService.findAll(PageRequest.of(page, size)
                        .withSort(Sort.by(sortBy)))
                .stream()
                .map(user -> modelMapper.map(user, UserPayload.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserPayload> getUserByUsername(
            @PathVariable @Size(min = 5, max = 255) final String username) {
        var optUser = userService.findByUsername(username);
        return optUser.map(user -> ResponseEntity.ok(modelMapper.map(user, UserPayload.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}/topics")
    public ResponseEntity<Set<TopicPayload>> getTopicsByUsername(
            @PathVariable @Size(min = 5, max = 255) final String username) {
        var optUser = userService.findByUsername(username);
        return optUser.map(user -> ResponseEntity.ok(user.getTopics().stream()
                        .map(topic -> modelMapper.map(topic, TopicPayload.class))
                        .collect(Collectors.toSet())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<Set<PostPayload>> addPostsByUsername(
            @PathVariable @Size(min = 5, max = 255) final String username) {
        var optUser = userService.findByUsername(username);
        return optUser.map(user -> ResponseEntity.ok(user.getPosts().stream()
                        .map(post -> modelMapper.map(post, PostPayload.class))
                        .collect(Collectors.toSet())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
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

    @DeleteMapping("/{username}")
    public ResponseEntity<UserPayload> deleteUserById(
            @PathVariable @Size(min = 5, max = 255) final String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserPayload> updateUser(
            @RequestBody @Valid final UserBasePayload payload,
            @PathVariable @Size(min = 5, max = 255) final String username) {
        return userService.updateUser(payload, username) ?
                ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
}
