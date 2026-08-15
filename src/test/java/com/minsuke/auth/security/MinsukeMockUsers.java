package com.minsuke.auth.security;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.entity.User;

public final class MinsukeMockUsers {

    private MinsukeMockUsers() {
    }

    public static RequestPostProcessor parent() {
        return user(Role.PARENT, 2L, 1L);
    }

    public static RequestPostProcessor admin() {
        return user(Role.ADMIN, 1L, null);
    }

    private static RequestPostProcessor user(Role role, Long id, Long householdId) {
        User entity = new User();
        entity.setId(id);
        entity.setEmail(role == Role.ADMIN ? "admin@test.local" : "parent@test.local");
        entity.setPasswordHash("hash");
        entity.setRole(role);
        entity.setHouseholdId(householdId);
        return SecurityMockMvcRequestPostProcessors.user(new MinsukeUserDetails(entity));
    }
}
