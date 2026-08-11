package com.minsuke.family.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.LocalDate;

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
import com.minsuke.family.dto.ChildForm;
import com.minsuke.family.dto.HouseholdForm;
import com.minsuke.family.dto.ParentForm;
import com.minsuke.family.entity.Household;
import com.minsuke.family.exception.FamilyAccessDeniedException;
import com.minsuke.family.exception.FamilyNotFoundException;
import com.minsuke.family.repository.ChildRepository;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.family.repository.ParentRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class FamilyServiceTest {

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
    private FamilyService familyService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserRepository userRepository;

    private Household householdA;
    private Household householdB;
    private MinsukeUserDetails parentUser;
    private MinsukeUserDetails adminUser;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();

        householdA = new Household();
        householdA.setName("A家");
        householdA.setNameKana("えーけ");
        householdA.setGroupName("1班");
        householdA.setCreatedAt(now);
        householdA.setUpdatedAt(now);
        householdA = householdRepository.save(householdA);

        householdB = new Household();
        householdB.setName("B家");
        householdB.setNameKana("びーけ");
        householdB.setCreatedAt(now);
        householdB.setUpdatedAt(now);
        householdB = householdRepository.save(householdB);

        User parent = new User();
        parent.setEmail("parent-a@test.local");
        parent.setPasswordHash("hash");
        parent.setRole(Role.PARENT);
        parent.setHouseholdId(householdA.getId());
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parent = userRepository.save(parent);
        parentUser = new MinsukeUserDetails(parent);

        User admin = new User();
        admin.setEmail("admin@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setHouseholdId(null);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        admin = userRepository.save(admin);
        adminUser = new MinsukeUserDetails(admin);
    }

    @Test
    void listHouseholdsReturnsAll() {
        assertThat(familyService.listHouseholds()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void parentCanUpdateOwnHousehold() {
        HouseholdForm form = new HouseholdForm();
        form.setName("A家改");
        form.setNameKana("えーけかい");
        form.setGroupName("2班");

        familyService.updateMyHousehold(parentUser, form);

        Household updated = householdRepository.findById(householdA.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("A家改");
        assertThat(updated.getGroupName()).isEqualTo("2班");
    }

    @Test
    void adminCannotUpdateHouseholdViaParentApi() {
        HouseholdForm form = new HouseholdForm();
        form.setName("不正");
        form.setNameKana("ふせい");
        form.setGroupName(null);

        assertThatThrownBy(() -> familyService.updateMyHousehold(adminUser, form))
                .isInstanceOf(FamilyAccessDeniedException.class);
    }

    @Test
    void parentCanManageParentsAndChildren() {
        ParentForm parentForm = new ParentForm();
        parentForm.setName("太郎");
        parentForm.setNameKana("たろう");
        parentForm.setPhone("090-1111-2222");
        familyService.createParent(parentUser, parentForm);

        ChildForm childForm = new ChildForm();
        childForm.setName("花子");
        childForm.setNameKana("はなこ");
        childForm.setBirthDate(LocalDate.of(2018, 5, 1));
        familyService.createChild(parentUser, childForm);

        var detail = familyService.getMyHousehold(parentUser);
        assertThat(detail.getParents()).hasSize(1);
        assertThat(detail.getChildren()).hasSize(1);

        Long parentId = detail.getParents().get(0).getId();
        parentForm.setName("太郎改");
        familyService.updateParent(parentUser, parentId, parentForm);
        familyService.deleteParent(parentUser, parentId);

        assertThat(parentRepository.findByHouseholdIdOrderByIdAsc(householdA.getId())).isEmpty();
    }

    @Test
    void parentCannotAccessOtherHouseholdParent() {
        assertThatThrownBy(() -> familyService.toParentForm(parentUser, 99999L))
                .isInstanceOf(FamilyNotFoundException.class);
    }

    @Test
    void adminCanDeleteHouseholdWithoutUsers() {
        familyService.deleteHousehold(adminUser, householdB.getId());
        assertThat(householdRepository.findById(householdB.getId())).isEmpty();
    }

    @Test
    void adminCannotDeleteHouseholdWithUsers() {
        assertThatThrownBy(() -> familyService.deleteHousehold(adminUser, householdA.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ユーザー");
    }

    @Test
    void parentCannotDeleteHousehold() {
        assertThatThrownBy(() -> familyService.deleteHousehold(parentUser, householdB.getId()))
                .isInstanceOf(FamilyAccessDeniedException.class);
    }
}
