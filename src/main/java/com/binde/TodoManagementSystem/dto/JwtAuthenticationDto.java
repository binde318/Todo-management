package com.binde.TodoManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
}
