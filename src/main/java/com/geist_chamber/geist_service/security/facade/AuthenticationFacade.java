package com.geist_chamber.geist_service.security.facade;

import com.geist_chamber.geist_service.entity.Geist;

public interface AuthenticationFacade {
    Geist getAuthenticatedUser();
    boolean hasRole(String role);
}
