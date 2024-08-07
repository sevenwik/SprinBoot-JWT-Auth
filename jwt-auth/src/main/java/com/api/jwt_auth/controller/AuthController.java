package com.api.jwt_auth.controller;

import com.api.jwt_auth.models.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AuthController {
    @GetMapping("/token")
    public void getToken(@RequestBody String username, @RequestBody String password) {
        
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Customer username) {
        if(username.getEmail() ==  null || username.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return null;
    }
}
