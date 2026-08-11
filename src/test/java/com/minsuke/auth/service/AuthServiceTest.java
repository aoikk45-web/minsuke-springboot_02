package com.minsuke.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.dto.RegisterForm;
import com.minsuke.auth.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class AuthServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("minsuke")
            .withUsername("minsuke")
            .withPassword("minsuke");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerParentCreatesUserAndHousehold() {
        RegisterForm form = new RegisterForm();
        form.setEmail("new-parent@example.com");
        form.setPassword("password12");
        form.setConfirmPassword("password12");
        form.setHouseholdName("テスト家");
        form.setHouseholdNameKana("てすとけ");
        form.setGroupName("B班");

        authService.registerParent(form);

        var user = userRepository.findByEmail("new-parent@example.com").orElseThrow();
        assertThat(user.getRole()).isEqualTo(Role.PARENT);
        assertThat(user.getHouseholdId()).isNotNull();
        assertThat(user.getPasswordHash()).isNotEqualTo("password12");
    }

    @Test
    void registerRejectsMismatchedPasswords() {
        RegisterForm form = new RegisterForm();
        form.setEmail("bad@example.com");
        form.setPassword("password12");
        form.setConfirmPassword("different12");
        form.setHouseholdName("テスト家");
        form.setHouseholdNameKana("てすとけ");

        assertThatThrownBy(() -> authService.registerParent(form))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("一致");
    }
}
