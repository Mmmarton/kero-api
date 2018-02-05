package com.komak.kero.keroapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class TokenAuthProvider implements AuthenticationProvider {

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        String token = auth.getCredentials().toString();

        List<GrantedAuthority> grants = new ArrayList<>();
        KeroAuthentication keroAuthentication = new KeroAuthentication(token);

        if (authService.isAuthenticated(token)) {
            grants.add(new SimpleGrantedAuthority(AuthRoles.ROLE_MEMBER));
            keroAuthentication.setAuthorities(grants);
            keroAuthentication.setAuthenticated(true);
        } else {
            throw new BadCredentialsException("Invalid token " + token);
        }

        return keroAuthentication;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return (KeroAuthentication.class.isAssignableFrom(arg0));

    }
}