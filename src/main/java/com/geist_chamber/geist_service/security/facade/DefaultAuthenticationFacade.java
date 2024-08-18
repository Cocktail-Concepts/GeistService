package com.geist_chamber.geist_service.security.facade;

import com.geist_chamber.geist_service.entity.Geist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class DefaultAuthenticationFacade implements AuthenticationFacade {

    @Override
    public Geist getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (Geist) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public boolean hasRole(String role) {
        UserDetails user = getAuthenticatedUser();
        if (user != null) {
            return user.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
        }
        return false;
    }
}

