package com.example.springdevkpi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/")
    String teamInfo(){
        return "team-info";
    }

}
