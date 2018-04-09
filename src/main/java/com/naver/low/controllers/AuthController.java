package com.naver.low.controllers;

import com.naver.low.entities.Role;
import com.naver.low.entities.RoleName;
import com.naver.low.entities.User;
import com.naver.low.exceptions.AppException;
import com.naver.low.payloads.ApiResponse;
import com.naver.low.payloads.JwtAuthenticationResponse;
import com.naver.low.payloads.LoginRequest;
import com.naver.low.payloads.SignUpRequest;
import com.naver.low.repositories.RoleRepository;
import com.naver.low.repositories.UserRepository;
import com.naver.low.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUserEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address is already taken!"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getPassword());
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        RoleName role = null;
        switch (signUpRequest.getRole()) {
            case 1:
                role = RoleName.ROLE_USER;
                break;
            case 2:
                role = RoleName.ROLE_WEBTOONIST;
                break;
            case 3:
                role = RoleName.ROLE_ADMIN;
                break;

            default:
                log.error("default case occurred");
                break;
        }
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{userName}")
                .buildAndExpand(result.getUserName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully."));
    }
}
