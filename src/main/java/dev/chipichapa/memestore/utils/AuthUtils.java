package dev.chipichapa.memestore.utils;

import dev.chipichapa.memestore.exception.AppException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    public UserDetails getUserDetailsOrThrow() {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal != null) {
            return principal;
        }
        throw new AppException("UserDetails is null");
    }
}
