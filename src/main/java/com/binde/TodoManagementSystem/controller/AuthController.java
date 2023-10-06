package com.binde.TodoManagementSystem.controller;

import com.binde.TodoManagementSystem.dto.JwtAuthenticationDto;
import com.binde.TodoManagementSystem.dto.LoginDto;
import com.binde.TodoManagementSystem.dto.RegisterDto;
import com.binde.TodoManagementSystem.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    //Build a Register rest Api
@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
       String response=  authService.register(registerDto);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //build login Rest API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDto> login(@RequestBody LoginDto loginDto){
    JwtAuthenticationDto jwtAuthenticationDto= authService.login(loginDto);
    return new ResponseEntity<>(jwtAuthenticationDto, HttpStatus.CREATED);

    }
}
