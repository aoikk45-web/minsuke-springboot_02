package com.minsuke.event.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;

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
import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.event.dto.EventForm;
import com.minsuke.event.dto.HouseholdParticipationRateDTO;
import com.minsuke.event.exception.EventAccessDeniedException;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.family.dto.ParentForm;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.family.repository.ParentRepository;
import com.minsuke.family.service.FamilyService;
import com.minsuke.schedule.domain.ScheduleType;
import com.minsuke.schedule.dto.ScheduleForm;
import com.minsuke.schedule.service.ScheduleService;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class AdminParticipationServiceTest {

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
    private AdminParticipationService adminParticipationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private EventRepository eventRepository;

    private MinsukeUserDetails adminUser;
    private MinsukeUserDetails parentA;
    private MinsukeUserDetails parentB;
    private Long parentAId;
    private Long parentBId;
    private Long householdAId;
    private Long householdBId;
    private Long householdCId;
    private Long scheduleId;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();

        User admin = new User();
        admin.setEmail("admin-rate@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        admin = userRepository.save(admin);
        adminUser = new MinsukeUserDetails(admin);

        Household a = saveHousehold("A家", "えーけ", now);
        Household b = saveHousehold("B家", "びーけ", now);
        Household c = saveHousehold("C家", "しーけ", now);
        householdAId = a.getId();
        householdBId = b.getId();
        householdCId = c.getId();

        parentA = saveParentUser("parent-a-rate@test.local", a.getId(), now);
        parentB = saveParentUser("parent-b-rate@test.local", b.getId(), now);
        saveParentUser("parent-c-rate@test.local", c.getId(), now);

        ParentForm pf = new ParentForm();
        pf.setName("太郎");
        pf.setNameKana("たろう");
        familyService.createParent(parentA, pf);
        parentAId = parentRepository.findByHouseholdIdOrderByIdAsc(a.getId()).get(0).getId();

        pf.setName("次郎");
        pf.setNameKana("じろう");
        familyService.createParent(parentB, pf);
        parentBId = parentRepository.findByHouseholdIdOrderByIdAsc(b.getId()).get(0).getId();

        ScheduleForm scheduleForm = new ScheduleForm();
        scheduleForm.setTitle("旗当番");
        scheduleForm.setDescription("テスト");
        scheduleForm.setScheduleType(ScheduleType.WEEKLY);
        scheduleForm.setDaysOfWeek(java.util.List.of(1));
        scheduleForm.setStartTime(LocalTime.of(8, 0));
        scheduleForm.setEndTime(LocalTime.of(8, 30));
        scheduleForm.setValidFrom(LocalDate.now(ZoneId.of("Asia/Tokyo")).minusWeeks(4));
        scheduleForm.setValidUntil(LocalDate.now(ZoneId.of("Asia/Tokyo")).plusWeeks(4));
        scheduleForm.setCapacity(5);
        scheduleForm.setParticipationUnit(ParticipationUnit.PARENT);
        scheduleForm.setActive(true);
        scheduleId = scheduleService.create(adminUser, scheduleForm);

        LocalDate monday = LocalDate.now(ZoneId.of("Asia/Tokyo"))
                .with(java.time.DayOfWeek.MONDAY);
        createLinkedEvent(monday.minusWeeks(2));
        createLinkedEvent(monday.minusWeeks(1));
        createLinkedEvent(monday);
        createLinkedEvent(monday.plusWeeks(1));
    }

    @Test
    void scheduleRatesPreferLowParticipationFirstAndIncludeZeroHouseholds() {
        var events = eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(scheduleId);
        assertThat(events).hasSize(4);

        eventService.registerParent(parentA, events.get(0).getId(), parentAId);
        eventService.registerParent(parentA, events.get(1).getId(), parentAId);
        eventService.registerParent(parentA, events.get(2).getId(), parentAId);
        eventService.registerParent(parentB, events.get(0).getId(), parentBId);

        var view = adminParticipationService.listScheduleHouseholdRates(adminUser, scheduleId, null);
        assertThat(view.getTotalEvents()).isEqualTo(4);
        assertThat(view.getHouseholds()).extracting(HouseholdParticipationRateDTO::getHouseholdName)
                .containsExactly("C家", "B家", "A家");
        assertThat(view.getHouseholds().get(0).getRatePercent()).isZero();
        assertThat(view.getHouseholds().get(1).getAttendedCount()).isEqualTo(1);
        assertThat(view.getHouseholds().get(1).getRatePercent()).isEqualTo(25);
        assertThat(view.getHouseholds().get(2).getAttendedCount()).isEqualTo(3);
        assertThat(view.getHouseholds().get(2).getRatePercent()).isEqualTo(75);
    }

    @Test
    void monthFilterLimitsDenominator() {
        var events = eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(scheduleId);
        eventService.registerParent(parentA, events.get(0).getId(), parentAId);

        YearMonth firstMonth = YearMonth.from(events.get(0).getEventDate());
        var view = adminParticipationService.listScheduleHouseholdRates(adminUser, scheduleId, firstMonth);
        long expectedTotal = events.stream()
                .filter(e -> YearMonth.from(e.getEventDate()).equals(firstMonth))
                .count();
        assertThat(view.getTotalEvents()).isEqualTo((int) expectedTotal);
    }

    @Test
    void monthlyFillsIncludeHandmadeEvents() {
        EventForm form = new EventForm();
        form.setTitle("手作り");
        form.setDescription("説明");
        form.setEventDate(LocalDate.now(ZoneId.of("Asia/Tokyo")));
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(LocalTime.of(12, 0));
        form.setCapacity(10);
        form.setParticipationUnit(ParticipationUnit.PARENT);
        Long handmadeId = eventService.createEvent(adminUser, form);
        eventService.registerParent(parentA, handmadeId, parentAId);

        var fills = adminParticipationService.listMonthlyEventFills(
                adminUser, YearMonth.from(form.getEventDate()));
        assertThat(fills).anySatisfy(row -> {
            assertThat(row.getEventId()).isEqualTo(handmadeId);
            assertThat(row.getRegisteredCount()).isEqualTo(1);
            assertThat(row.getRemaining()).isEqualTo(9);
            assertThat(row.getScheduleTitle()).isNull();
        });
    }

    @Test
    void parentCannotViewAdminRates() {
        assertThatThrownBy(() -> adminParticipationService.listScheduleHouseholdRates(parentA, scheduleId, null))
                .isInstanceOf(EventAccessDeniedException.class);
    }

    private Household saveHousehold(String name, String kana, Instant now) {
        Household household = new Household();
        household.setName(name);
        household.setNameKana(kana);
        household.setCreatedAt(now);
        household.setUpdatedAt(now);
        return householdRepository.save(household);
    }

    private MinsukeUserDetails saveParentUser(String email, Long householdId, Instant now) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash("hash");
        user.setRole(Role.PARENT);
        user.setHouseholdId(householdId);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return new MinsukeUserDetails(userRepository.save(user));
    }

    private void createLinkedEvent(LocalDate date) {
        EventForm form = new EventForm();
        form.setTitle("旗当番");
        form.setDescription("週次");
        form.setEventDate(date);
        form.setStartTime(LocalTime.of(8, 0));
        form.setEndTime(LocalTime.of(8, 30));
        form.setCapacity(5);
        form.setParticipationUnit(ParticipationUnit.PARENT);
        Long eventId = eventService.createEvent(adminUser, form);
        var event = eventRepository.findById(eventId).orElseThrow();
        event.setScheduleId(scheduleId);
        eventRepository.save(event);
    }
}
