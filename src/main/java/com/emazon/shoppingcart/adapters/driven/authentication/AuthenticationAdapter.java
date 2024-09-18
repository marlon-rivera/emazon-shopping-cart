package com.emazon.shoppingcart.adapters.driven.authentication;

import com.emazon.shoppingcart.domain.spi.IAuthenticationPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationAdapter implements IAuthenticationPort {
    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }
}
