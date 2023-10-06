package com.binde.TodoManagementSystem.service;

import com.binde.TodoManagementSystem.dto.JwtAuthenticationDto;
import com.binde.TodoManagementSystem.dto.LoginDto;
import com.binde.TodoManagementSystem.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthenticationDto login(LoginDto loginDto);
}
