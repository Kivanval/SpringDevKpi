package com.example.springdevkpi.service.loader;

import com.example.springdevkpi.service.DefaultRoles;
import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.web.data.transfer.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserLoader implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.signUp(new Credentials("kivanval", "12345678"));
        userService.signUp(new Credentials("bhurov", "qwerty123"), DefaultRoles.ADMIN);
        userService.signUp(new Credentials("nikitochkaa", "8642ruchka"), DefaultRoles.SUPER_ADMIN);
    }
}
