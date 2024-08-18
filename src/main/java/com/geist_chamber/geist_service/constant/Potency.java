package com.geist_chamber.geist_service.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Potency implements GrantedAuthority {
    ADMIN,COMPANION,ORGANIZER,CONTRIBUTOR,HARMONIZER,CONQUEROR,OG;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
