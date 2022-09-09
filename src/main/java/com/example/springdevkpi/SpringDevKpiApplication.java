package com.example.springdevkpi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDevKpiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("Begin of main");
        SpringApplication.run(SpringDevKpiApplication.class, args);
        System.out.println("End of main");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello from Spring Boot!");
    }
}
