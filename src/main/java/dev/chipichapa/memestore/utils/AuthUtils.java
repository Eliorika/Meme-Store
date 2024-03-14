package dev.chipichapa.memestore.utils;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.exception.AppException;
import dev.chipichapa.memestore.service.ifc.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UserService userService;

    public UserDetails getUserDetailsOrThrow() {
        UserDetails principal =  getUserDetails();

        if (principal != null) {
            return principal;
        }
        throw new AppException("UserDetails is null");
    }

    public Optional<UserDetails> getUserDetailsOrNull() {
        UserDetails principal =  getUserDetails();

        if (principal != null) {
            return Optional.of(principal);
        }
        return Optional.empty();
    }


    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null){
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }

    public User getUserEntity() {
        UserDetails userDetails = getUserDetailsOrThrow();
        return userService.getByUsername(userDetails.getUsername());
    }
}
