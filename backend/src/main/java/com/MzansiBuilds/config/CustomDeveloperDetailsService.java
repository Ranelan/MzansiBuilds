package com.MzansiBuilds.config;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDeveloperDetailsService implements UserDetailsService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Developer developer = developerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Developer not found: " + username));

        return User.withUsername(developer.getUsername())
                .password(developer.getPassword())
                .authorities("ROLE_DEVELOPER")
                .build();
    }
}

