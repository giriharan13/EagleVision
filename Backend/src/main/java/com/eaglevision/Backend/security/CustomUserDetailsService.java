package com.eaglevision.Backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.UserRepository;
import com.eaglevision.Backend.service.AuthorityUtilityService;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AuthorityUtilityService authorityUtilityService;
    
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AuthorityUtilityService authorityUtilityService,UserRepository userRepository) {
        this.authorityUtilityService = authorityUtilityService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorityUtilityService.getRoles(user));
    }
    
}
