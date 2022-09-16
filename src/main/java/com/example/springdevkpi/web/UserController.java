package com.example.springdevkpi.web;

import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.web.dto.Credentials;
import com.example.springdevkpi.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private static final String USER_PROPERTIES = "id|username|createdAt|role_id";

    @GetMapping("/")
    public Collection<UserDto> getUsers(
            @RequestParam(defaultValue = "0") @Min(0) final int size,
            @RequestParam(defaultValue = "20") @Min(1) final int page,
            @RequestParam(defaultValue = "id") @Pattern(regexp = USER_PROPERTIES) final String sortBy) {
        return userService.findAll(PageRequest.of(page, size)
                        .withSort(Sort.by(sortBy)))
                .stream()
                .map(userService::of)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable final long id) {
        var optUser = userService.findById(id);
        return optUser.map(user -> ResponseEntity.ok(userService.of(user)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(
            @PathVariable final String username) {
        var optUser = userService.findByUsername(username);
        return optUser.map(user -> ResponseEntity.ok(userService.of(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> postUser(
            @RequestBody @Valid final Credentials credentials) {
        var user = userService.fillCredentials(credentials);
        userService.save(user);
        return ResponseEntity.ok(userService.of(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUserById(
            @PathVariable final long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
