package com.geist_chamber.geist_service.security;

import com.geist_chamber.geist_service.entity.Geist;
import com.geist_chamber.geist_service.repository.GeistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    GeistRepository geistRepository;

    @Override
    public Geist loadUserByUsername(String username) throws UsernameNotFoundException {
        Geist geist = this.geistRepository.findByGeistname(username);
        if (geist == null) {
            throw new UsernameNotFoundException("geist " + username + " is not found");
        }
        return geist;
    }
}
