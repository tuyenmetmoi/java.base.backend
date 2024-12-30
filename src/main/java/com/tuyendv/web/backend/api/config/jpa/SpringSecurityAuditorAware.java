package com.tuyendv.web.backend.api.config.jpa;

import com.tuyendv.web.backend.api.common.GlobalEnum;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || ! authentication.isAuthenticated()) {
            return Optional.of(GlobalEnum.AnonymousUser_String.toString());
        }

        String userId = getUserIdFromAuthentication(authentication);

        return Optional.ofNullable(userId);
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return GlobalEnum.AnonymousUser_String.toString();
    }

}