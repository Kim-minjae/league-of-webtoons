package com.naver.low.controllers;

import com.naver.low.entities.User;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.ApiResponse;
import com.naver.low.payloads.SignUpRequest;
import com.naver.low.payloads.UserProfile;
import com.naver.low.payloads.UserSummary;
import com.naver.low.repositories.UserRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    UserRepository userRepository;

    @GetMapping("/me")
    // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WEBTOONIST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return  new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail());
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfile(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "userid", id));
        return new UserProfile(user.getId(), user.getUserName(), user.getUserEmail());
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody SignUpRequest updateRequest, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "userid", currentUser.getId()));
        user.setUserName(updateRequest.getUsername());
        user.setUserEmail(updateRequest.getEmail());
        // is it okay to have password update login here?
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully."));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "userid", currentUser.getId()));
        userRepository.delete(user);
        return ResponseEntity.ok((new ApiResponse(true, "User deleted successfully.")));
    }
}
