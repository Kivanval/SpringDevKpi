package com.example.springdevkpi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/")
    @ResponseBody
    String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/team")
    String teamInfo(){
        return "team-info";
    }

    @GetMapping("/team/mykyta")
    String mykytaInfo(){
        return "mykyta";
    }

    @GetMapping("/team/ivan")
    String ivanInfo(){
        return "ivan";
    }

    @GetMapping("/team/bogdan")
    String bogdanInfo(){
        return "bogdan";
    }
}
