package com.api.jwt_auth.controller;

import com.api.jwt_auth.models.Customer;
import com.api.jwt_auth.models.Token;
import com.api.jwt_auth.util.JWTHelper;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Object> register(@RequestBody Customer cust) {
        if(cust.getEmail() ==  null || cust.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Token token = new Token();
        token.setToken(JWTHelper.createToken(cust.getName()));
        return new ResponseEntity<>(token.getToken(), HttpStatusCode.valueOf(200));
    }
}
