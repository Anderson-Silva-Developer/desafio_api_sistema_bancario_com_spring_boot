package br.com.anderson_silva.Banking_system.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Bank")
public class UserController {
    @GetMapping("/")
    public  String hello(){
        return  "Bank ok!";
    }
}
