package com.naver.low.controllers;

import com.naver.low.entities.User;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.SignUpRequest;
import com.naver.low.payloads.UserProfile;
import com.naver.low.payloads.UserSummary;
import com.naver.low.repositories.RoleRepository;
import com.naver.low.repositories.UserRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    UserRepository userRepository;

    @GetMapping("/user/me")
    // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WEBTOONIST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail());
        return userSummary;
    }

    @GetMapping("/users/{id}")
    public UserProfile getUserProfile(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "userid", id));
        return new UserProfile(user.getId(), user.getUserName(), user.getUserEmail());
    }

    /*@PutMapping("/users/me")
    public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest updateRequest, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "userid", currentUser.getId()));
        user.setUserName(updateRequest.getUsername());
        user.setUserEmail(updateRequest.getEmail());
        return
    }*/
}
