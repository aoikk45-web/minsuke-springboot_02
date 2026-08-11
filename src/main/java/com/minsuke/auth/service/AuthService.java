package com.minsuke.auth.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.dto.RegisterForm;
import com.minsuke.auth.entity.User;
import com.minsuke.auth.repository.UserRepository;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HouseholdRepository householdRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            HouseholdRepository householdRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerParent(RegisterForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new IllegalArgumentException("パスワードが一致しません");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("このメールアドレスは既に登録されています");
        }

        Instant now = Instant.now();

        Household household = new Household();
        household.setName(form.getHouseholdName());
        household.setNameKana(form.getHouseholdNameKana());
        household.setGroupName(emptyToNull(form.getGroupName()));
        household.setCreatedAt(now);
        household.setUpdatedAt(now);
        household = householdRepository.save(household);

        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole(Role.PARENT);
        user.setHouseholdId(household.getId());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
