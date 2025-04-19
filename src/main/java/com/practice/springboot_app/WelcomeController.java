package com.practice.springboot_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to java";
    }
}
