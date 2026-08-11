package com.minsuke.instructor.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
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
import com.minsuke.auth.entity.User;
import com.minsuke.auth.repository.UserRepository;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.instructor.dto.InstructorForm;
import com.minsuke.instructor.exception.InstructorAccessDeniedException;
import com.minsuke.instructor.exception.InstructorNotFoundException;
import com.minsuke.instructor.repository.InstructorRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class InstructorServiceTest {

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
    private InstructorService instructorService;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    private MinsukeUserDetails adminUser;
    private MinsukeUserDetails parentUser;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();

        Household household = new Household();
        household.setName("A家");
        household.setNameKana("えーけ");
        household.setCreatedAt(now);
        household.setUpdatedAt(now);
        household = householdRepository.save(household);

        User admin = new User();
        admin.setEmail("admin-i@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        adminUser = new MinsukeUserDetails(userRepository.save(admin));

        User parent = new User();
        parent.setEmail("parent-i@test.local");
        parent.setPasswordHash("hash");
        parent.setRole(Role.PARENT);
        parent.setHouseholdId(household.getId());
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parentUser = new MinsukeUserDetails(userRepository.save(parent));
    }

    @Test
    void adminCanCreateAndUpdateInstructor() {
        InstructorForm form = sampleForm();
        Long id = instructorService.create(adminUser, form);

        assertThat(instructorRepository.findById(id)).isPresent();
        assertThat(instructorService.getInstructor(id, parentUser).getName()).isEqualTo("佐藤 講師");

        form.setName("佐藤 改");
        instructorService.update(adminUser, id, form);
        assertThat(instructorService.getInstructor(id, adminUser).getName()).isEqualTo("佐藤 改");
    }

    @Test
    void parentCannotCreateInstructor() {
        assertThatThrownBy(() -> instructorService.create(parentUser, sampleForm()))
                .isInstanceOf(InstructorAccessDeniedException.class);
    }

    @Test
    void parentDoesNotSeeInactiveInstructors() {
        InstructorForm form = sampleForm();
        form.setActive(false);
        Long id = instructorService.create(adminUser, form);

        assertThat(instructorService.listInstructors(parentUser))
                .noneMatch(i -> i.getId().equals(id));
        assertThatThrownBy(() -> instructorService.getInstructor(id, parentUser))
                .isInstanceOf(InstructorNotFoundException.class);

        assertThat(instructorService.listInstructors(adminUser))
                .anyMatch(i -> i.getId().equals(id) && !i.isActive());
    }

    @Test
    void adminCanDeactivateAndDelete() {
        Long id = instructorService.create(adminUser, sampleForm());
        instructorService.deactivate(adminUser, id);
        assertThat(instructorService.getInstructor(id, adminUser).isActive()).isFalse();

        instructorService.delete(adminUser, id);
        assertThat(instructorRepository.findById(id)).isEmpty();
    }

    private InstructorForm sampleForm() {
        InstructorForm form = new InstructorForm();
        form.setName("佐藤 講師");
        form.setNameKana("さとう こうし");
        form.setEmail("sato@example.com");
        form.setPhone("090-1111-2222");
        form.setNotes("テスト");
        form.setActive(true);
        return form;
    }
}
