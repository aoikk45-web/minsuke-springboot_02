package com.minsuke.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.minsuke.event.repository.EventRepository;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.schedule.domain.ScheduleType;
import com.minsuke.schedule.dto.ScheduleForm;
import com.minsuke.schedule.dto.ScheduleGenerateResultDTO;
import com.minsuke.schedule.exception.ScheduleAccessDeniedException;
import com.minsuke.schedule.repository.ScheduleRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class ScheduleServiceTest {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");

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
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EventRepository eventRepository;

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
        admin.setEmail("admin-s@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        adminUser = new MinsukeUserDetails(userRepository.save(admin));

        User parent = new User();
        parent.setEmail("parent-s@test.local");
        parent.setPasswordHash("hash");
        parent.setRole(Role.PARENT);
        parent.setHouseholdId(household.getId());
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parentUser = new MinsukeUserDetails(userRepository.save(parent));
    }

    @Test
    void adminCanCreateOneOffScheduleAndGenerateEvent() {
        LocalDate futureDate = LocalDate.now(ZONE).plusDays(7);
        ScheduleForm form = oneOffForm(futureDate);
        Long id = scheduleService.create(adminUser, form);

        assertThat(scheduleRepository.findById(id)).isPresent();

        ScheduleGenerateResultDTO result = scheduleService.generateEvents(adminUser, id, 4);
        assertThat(result.getCreatedCount()).isEqualTo(1);
        assertThat(result.getSkippedCount()).isZero();
        assertThat(eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(id)).hasSize(1);
    }

    @Test
    void generateSkipsDuplicateDates() {
        LocalDate futureDate = LocalDate.now(ZONE).plusDays(3);
        Long id = scheduleService.create(adminUser, oneOffForm(futureDate));

        scheduleService.generateEvents(adminUser, id, 4);
        ScheduleGenerateResultDTO second = scheduleService.generateEvents(adminUser, id, 4);

        assertThat(second.getCreatedCount()).isZero();
        assertThat(second.getSkippedCount()).isEqualTo(1);
    }

    @Test
    void adminCanCreateWeeklySchedule() {
        ScheduleForm form = weeklyForm(DayOfWeek.MONDAY);
        Long id = scheduleService.create(adminUser, form);

        assertThat(scheduleService.getDetail(id, adminUser).getScheduleType()).isEqualTo(ScheduleType.WEEKLY);
        ScheduleGenerateResultDTO result = scheduleService.generateEvents(adminUser, id, 4);
        assertThat(result.getCreatedCount()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void weeklyScheduleGeneratesEventsForAllSelectedDays() {
        ScheduleForm form = weeklyForm(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        Long id = scheduleService.create(adminUser, form);

        assertThat(scheduleService.getDetail(id, adminUser).getDaysOfWeekLabel()).isEqualTo("月曜日・水曜日");

        scheduleService.generateEvents(adminUser, id, 4);
        Set<DayOfWeek> generatedDays = eventRepository
                .findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(id)
                .stream()
                .map(event -> event.getEventDate().getDayOfWeek())
                .collect(Collectors.toSet());
        assertThat(generatedDays).contains(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        assertThat(generatedDays).hasSize(2);
    }

    @Test
    void parentCannotAccessSchedules() {
        assertThatThrownBy(() -> scheduleService.list(parentUser))
                .isInstanceOf(ScheduleAccessDeniedException.class);
    }

    private ScheduleForm oneOffForm(LocalDate date) {
        ScheduleForm form = new ScheduleForm();
        form.setTitle("単発レッスン");
        form.setDescription("説明");
        form.setScheduleType(ScheduleType.ONE_OFF);
        form.setOneOffDate(date);
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(LocalTime.of(11, 0));
        form.setCapacity(10);
        form.setActive(true);
        return form;
    }

    private ScheduleForm weeklyForm(DayOfWeek... daysOfWeek) {
        ScheduleForm form = new ScheduleForm();
        form.setTitle("毎週レッスン");
        form.setDescription("説明");
        form.setScheduleType(ScheduleType.WEEKLY);
        form.setDaysOfWeek(List.of(daysOfWeek).stream().map(DayOfWeek::getValue).toList());
        form.setValidFrom(LocalDate.now(ZONE));
        form.setStartTime(LocalTime.of(14, 0));
        form.setEndTime(LocalTime.of(15, 0));
        form.setActive(true);
        return form;
    }
}
