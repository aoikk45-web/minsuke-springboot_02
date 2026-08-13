package com.minsuke.announcement.service;

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

import com.minsuke.announcement.dto.AnnouncementForm;
import com.minsuke.announcement.exception.AnnouncementAccessDeniedException;
import com.minsuke.announcement.repository.AnnouncementReadRepository;
import com.minsuke.announcement.repository.AnnouncementRepository;
import com.minsuke.auth.domain.Role;
import com.minsuke.auth.entity.User;
import com.minsuke.auth.repository.UserRepository;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class AnnouncementServiceTest {

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
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementReadRepository announcementReadRepository;

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
        admin.setEmail("admin-a@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        adminUser = new MinsukeUserDetails(userRepository.save(admin));

        User parent = new User();
        parent.setEmail("parent-a@test.local");
        parent.setPasswordHash("hash");
        parent.setRole(Role.PARENT);
        parent.setHouseholdId(household.getId());
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parentUser = new MinsukeUserDetails(userRepository.save(parent));
    }

    @Test
    void adminCanCreateAndParentCanRead() {
        AnnouncementForm form = sampleForm();
        Long id = announcementService.create(adminUser, form);

        assertThat(announcementRepository.findById(id)).isPresent();
        assertThat(announcementService.countUnread(parentUser)).isEqualTo(1);

        var detail = announcementService.getDetail(id, parentUser);
        assertThat(detail.getTitle()).isEqualTo("休講のお知らせ");
        assertThat(detail.isRead()).isTrue();
        assertThat(announcementReadRepository.existsByAnnouncementIdAndUserId(id, parentUser.getUser().getId()))
                .isTrue();
        assertThat(announcementService.countUnread(parentUser)).isZero();
    }

    @Test
    void parentCannotCreate() {
        assertThatThrownBy(() -> announcementService.create(parentUser, sampleForm()))
                .isInstanceOf(AnnouncementAccessDeniedException.class);
    }

    @Test
    void adminCanUpdateAndDelete() {
        Long id = announcementService.create(adminUser, sampleForm());

        AnnouncementForm form = sampleForm();
        form.setTitle("更新後");
        announcementService.update(adminUser, id, form);
        assertThat(announcementService.getDetail(id, adminUser).getTitle()).isEqualTo("更新後");

        announcementService.delete(adminUser, id);
        assertThat(announcementRepository.findById(id)).isEmpty();
    }

    private AnnouncementForm sampleForm() {
        AnnouncementForm form = new AnnouncementForm();
        form.setTitle("休講のお知らせ");
        form.setBody("明日のクラスは休講です。");
        return form;
    }
}
