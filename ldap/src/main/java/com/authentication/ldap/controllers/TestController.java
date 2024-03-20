package com.authentication.ldap.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/get")
    public String getData(){
        return "Hi! Welcome to the home page!";
    }
}
