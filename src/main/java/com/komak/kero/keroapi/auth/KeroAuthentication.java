package com.komak.kero.keroapi.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class KeroAuthentication implements Authentication {

    private static final long serialVersionUID = 1L;

    private String token;
    private Collection<? extends GrantedAuthority> grants;

    public KeroAuthentication() {

    }

    public KeroAuthentication(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grants;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean arg0) throws IllegalArgumentException {

    }

    public void setAuthorities(Collection<? extends GrantedAuthority> grants) {
        this.grants = grants;
    }
}