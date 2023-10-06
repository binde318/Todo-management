package com.binde.TodoManagementSystem.service.impl;

import com.binde.TodoManagementSystem.dto.JwtAuthenticationDto;
import com.binde.TodoManagementSystem.dto.LoginDto;
import com.binde.TodoManagementSystem.dto.RegisterDto;
import com.binde.TodoManagementSystem.entity.Role;
import com.binde.TodoManagementSystem.entity.User;
import com.binde.TodoManagementSystem.exception.TodoApiException;
import com.binde.TodoManagementSystem.repository.RoleRepository;
import com.binde.TodoManagementSystem.repository.UserRepository;
import com.binde.TodoManagementSystem.security.JwtTokenProvider;
import com.binde.TodoManagementSystem.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {

        // if the username already exist in the database

        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,"The user has already exist");
        }
        //check if the email is already existed in the database
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST, " The email has already existed!");
        }
        //check if the password already existed in the database
//        if (userRepository.existsByPassword(registerDto.getPassword())){
//            throw new TodoApiException(HttpStatus.BAD_REQUEST, "The user with the password has already existed!");
//        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER_ROLE");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        return " user register successfully";
    }

    @Override
    public JwtAuthenticationDto login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional =userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
                loginDto.getUsernameOrEmail());
        String role = null;
        if (userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if (optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setRole(role);
        jwtAuthenticationDto.setAccessToken(token);
        return jwtAuthenticationDto;
    }
}
