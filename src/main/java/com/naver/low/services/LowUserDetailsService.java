package com.naver.low.services;

import com.naver.low.entities.Role;
import com.naver.low.entities.User;
import com.naver.low.repositories.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LowUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LowUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserEmail(email);
        List<Role> roles = user.getRoles();
        String[] s = new String[roles.size()];
        int i = 0;
        for (Role role : roles) {
            s[i++] = role.getName();
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserEmail(),
                user.getUserPassword(),
                AuthorityUtils.createAuthorityList(s));
    }
}
