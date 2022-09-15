package com.jwtexample.jwtdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
    @GetMapping("/inicio")
    public String sayHello(){
        return "hola from desde el controller home";
    }
}
