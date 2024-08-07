package com.api.jwt_auth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AuthController {
    @GetMapping("/token")
    public void getToken(@RequestBody String username, @RequestBody String password) {
        
    }

    @PostMapping("/register")
    public void register(@RequestBody String username, @RequestBody String password) {

    }
}
