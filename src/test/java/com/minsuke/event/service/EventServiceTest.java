package com.minsuke.event.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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
import com.minsuke.event.domain.AttendanceStatus;
import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.event.dto.EventForm;
import com.minsuke.event.dto.SeriesAttendResultDTO;
import com.minsuke.event.entity.Event;
import com.minsuke.event.exception.EventAccessDeniedException;
import com.minsuke.event.exception.EventCapacityFullException;
import com.minsuke.event.exception.EventNotFoundException;
import com.minsuke.event.repository.EventAttendanceRepository;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.family.dto.ChildForm;
import com.minsuke.family.dto.ParentForm;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.ChildRepository;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.family.repository.ParentRepository;
import com.minsuke.family.service.FamilyService;
import com.minsuke.instructor.entity.Instructor;
import com.minsuke.instructor.repository.InstructorRepository;
import com.minsuke.schedule.domain.ScheduleType;
import com.minsuke.schedule.entity.Schedule;
import com.minsuke.schedule.repository.ScheduleRepository;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class EventServiceTest {

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
    private EventService eventService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventAttendanceRepository attendanceRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private MinsukeUserDetails adminUser;
    private MinsukeUserDetails parentUser;
    private Long parentId;
    private Long childId;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();

        Household household = new Household();
        household.setName("A家");
        household.setNameKana("えーけ");
        household.setCreatedAt(now);
        household.setUpdatedAt(now);
        household = householdRepository.save(household);

        User parent = new User();
        parent.setEmail("parent@test.local");
        parent.setPasswordHash("hash");
        parent.setRole(Role.PARENT);
        parent.setHouseholdId(household.getId());
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parent = userRepository.save(parent);
        parentUser = new MinsukeUserDetails(parent);

        User admin = new User();
        admin.setEmail("admin@test.local");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        admin = userRepository.save(admin);
        adminUser = new MinsukeUserDetails(admin);

        ParentForm parentForm = new ParentForm();
        parentForm.setName("太郎");
        parentForm.setNameKana("たろう");
        familyService.createParent(parentUser, parentForm);
        parentId = parentRepository.findByHouseholdIdOrderByIdAsc(household.getId()).get(0).getId();

        ChildForm childForm = new ChildForm();
        childForm.setName("花子");
        childForm.setNameKana("はなこ");
        familyService.createChild(parentUser, childForm);
        childId = childRepository.findByHouseholdIdOrderByIdAsc(household.getId()).get(0).getId();
    }

    @Test
    void adminCanCreateEvent() {
        EventForm form = sampleEventForm();
        Long eventId = eventService.createEvent(adminUser, form);

        assertThat(eventRepository.findById(eventId)).isPresent();
        assertThat(eventRepository.findById(eventId).orElseThrow().getTitle()).isEqualTo("運動会");
    }

    @Test
    void parentCannotCreateEvent() {
        assertThatThrownBy(() -> eventService.createEvent(parentUser, sampleEventForm()))
                .isInstanceOf(EventAccessDeniedException.class);
    }

    @Test
    void calendarViewIncludesCreatedEvent() {
        Long eventId = eventService.createEvent(adminUser, sampleEventForm());
        var calendar = eventService.buildCalendarView(
                sampleEventForm().getEventDate().getYear(),
                sampleEventForm().getEventDate().getMonthValue(),
                adminUser);

        assertThat(calendar.getWeeks()).isNotEmpty();
        boolean found = calendar.getWeeks().stream()
                .flatMap(w -> w.getDays().stream())
                .flatMap(d -> d.getEvents().stream())
                .anyMatch(e -> e.getId().equals(eventId));
        assertThat(found).isTrue();
    }

    @Test
    void parentCanRegisterAndCancel() {
        Long eventId = eventService.createEvent(adminUser, sampleEventForm());

        eventService.registerParent(parentUser, eventId, parentId);
        var detail = eventService.getEventDetail(eventId, parentUser);
        assertThat(detail.getRegisteredCount()).isEqualTo(1);
        assertThat(detail.getParticipantOptions().get(0).isRegistered()).isTrue();

        eventService.cancelParent(parentUser, eventId, parentId);
        detail = eventService.getEventDetail(eventId, parentUser);
        assertThat(detail.getRegisteredCount()).isZero();
    }

    @Test
    void capacityFullPreventsRegistration() {
        EventForm form = sampleEventForm();
        form.setCapacity(1);
        Long eventId = eventService.createEvent(adminUser, form);

        eventService.registerParent(parentUser, eventId, parentId);

        Household otherHousehold = new Household();
        Instant now = Instant.now();
        otherHousehold.setName("B家");
        otherHousehold.setNameKana("びーけ");
        otherHousehold.setCreatedAt(now);
        otherHousehold.setUpdatedAt(now);
        otherHousehold = householdRepository.save(otherHousehold);

        User otherParentUserEntity = new User();
        otherParentUserEntity.setEmail("other@test.local");
        otherParentUserEntity.setPasswordHash("hash");
        otherParentUserEntity.setRole(Role.PARENT);
        otherParentUserEntity.setHouseholdId(otherHousehold.getId());
        otherParentUserEntity.setCreatedAt(now);
        otherParentUserEntity.setUpdatedAt(now);
        otherParentUserEntity = userRepository.save(otherParentUserEntity);
        MinsukeUserDetails otherParentUser = new MinsukeUserDetails(otherParentUserEntity);

        ParentForm otherParentForm = new ParentForm();
        otherParentForm.setName("次郎");
        otherParentForm.setNameKana("じろう");
        familyService.createParent(otherParentUser, otherParentForm);
        Long otherParentId = parentRepository.findByHouseholdIdOrderByIdAsc(otherHousehold.getId()).get(0).getId();

        assertThatThrownBy(() -> eventService.registerParent(otherParentUser, eventId, otherParentId))
                .isInstanceOf(EventCapacityFullException.class);
    }

    @Test
    void parentCannotRegisterOtherHouseholdMember() {
        Long eventId = eventService.createEvent(adminUser, sampleEventForm());

        assertThatThrownBy(() -> eventService.registerParent(parentUser, eventId, 99999L))
                .isInstanceOf(EventAccessDeniedException.class);
    }

    @Test
    void getEventDetailThrowsWhenNotFound() {
        assertThatThrownBy(() -> eventService.getEventDetail(99999L, parentUser))
                .isInstanceOf(EventNotFoundException.class);
    }

    @Test
    void adminCanAssignAndUpdateInstructor() {
        Long instructorId = createActiveInstructor("山田 講師");
        EventForm form = sampleEventForm();
        form.setInstructorId(instructorId);

        Long eventId = eventService.createEvent(adminUser, form);
        var detail = eventService.getEventDetail(eventId, adminUser);
        assertThat(detail.getInstructorId()).isEqualTo(instructorId);
        assertThat(detail.getInstructorName()).isEqualTo("山田 講師");

        form.setInstructorId(null);
        eventService.updateEvent(adminUser, eventId, form);
        detail = eventService.getEventDetail(eventId, adminUser);
        assertThat(detail.getInstructorId()).isNull();
        assertThat(detail.getInstructorName()).isNull();
    }

    @Test
    void cannotAssignInactiveInstructor() {
        Long instructorId = createActiveInstructor("無効予定");
        var instructor = instructorRepository.findById(instructorId).orElseThrow();
        instructor.setActive(false);
        instructorRepository.save(instructor);

        EventForm form = sampleEventForm();
        form.setInstructorId(instructorId);

        assertThatThrownBy(() -> eventService.createEvent(adminUser, form))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("無効");
    }

    @Test
    void invalidTimeRangeRejected() {
        EventForm form = sampleEventForm();
        form.setStartTime(LocalTime.of(18, 0));
        form.setEndTime(LocalTime.of(9, 0));

        assertThatThrownBy(() -> eventService.createEvent(adminUser, form))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void childOnlyEventRejectsParentRegistration() {
        EventForm form = sampleEventForm();
        form.setParticipationUnit(ParticipationUnit.CHILD);
        Long eventId = eventService.createEvent(adminUser, form);

        assertThatThrownBy(() -> eventService.registerParent(parentUser, eventId, parentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("子ども");
        eventService.registerChild(parentUser, eventId, childId);
        assertThat(eventService.getEventDetail(eventId, parentUser).getRegisteredCount()).isEqualTo(1);
    }

    @Test
    void householdUnitRegistersOneSlotPerFamily() {
        EventForm form = sampleEventForm();
        form.setParticipationUnit(ParticipationUnit.HOUSEHOLD);
        form.setCapacity(1);
        Long eventId = eventService.createEvent(adminUser, form);

        eventService.registerHousehold(parentUser, eventId);
        var detail = eventService.getEventDetail(eventId, parentUser);
        assertThat(detail.getRegisteredCount()).isEqualTo(1);
        assertThat(detail.getParticipantOptions()).hasSize(1);
        assertThat(detail.getParticipantOptions().get(0).isRegistered()).isTrue();

        assertThatThrownBy(() -> eventService.registerParent(parentUser, eventId, parentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("家庭");
    }

    @Test
    void calendarHighlightsHouseholdParticipationAndListsToday() {
        EventForm form = sampleEventForm();
        form.setEventDate(LocalDate.now(ZoneId.of("Asia/Tokyo")));
        Long eventId = eventService.createEvent(adminUser, form);
        eventService.registerParent(parentUser, eventId, parentId);

        var parentCalendar = eventService.buildCalendarView(
                form.getEventDate().getYear(), form.getEventDate().getMonthValue(), parentUser);
        assertThat(parentCalendar.isShowHouseholdParticipation()).isTrue();
        assertThat(parentCalendar.getTodayParticipations())
                .extracting(e -> e.getId())
                .contains(eventId);
        boolean participating = parentCalendar.getWeeks().stream()
                .flatMap(w -> w.getDays().stream())
                .flatMap(d -> d.getEvents().stream())
                .anyMatch(e -> e.getId().equals(eventId) && e.isParticipating());
        assertThat(participating).isTrue();

        var adminCalendar = eventService.buildCalendarView(
                form.getEventDate().getYear(), form.getEventDate().getMonthValue(), adminUser);
        assertThat(adminCalendar.isShowHouseholdParticipation()).isFalse();
        assertThat(adminCalendar.getTodayParticipations()).isEmpty();
        boolean adminMarked = adminCalendar.getWeeks().stream()
                .flatMap(w -> w.getDays().stream())
                .flatMap(d -> d.getEvents().stream())
                .anyMatch(e -> e.getId().equals(eventId) && e.isParticipating());
        assertThat(adminMarked).isFalse();
    }

    @Test
    void handmadeEventDoesNotOfferSeriesAttendance() {
        Long eventId = eventService.createEvent(adminUser, sampleEventForm());
        var detail = eventService.getEventDetail(eventId, parentUser);
        assertThat(detail.isSeriesAttendanceAvailable()).isFalse();
        assertThatThrownBy(() -> eventService.registerParentSeries(parentUser, eventId, parentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("スケジュール");
    }

    @Test
    void parentCanRegisterSeriesSkippingFullAndPastThenCancel() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tokyo"));
        Long pastId = createEventOn(today.minusDays(7), null);
        Long todayId = createEventOn(today, 1);
        Long futureOpenId = createEventOn(today.plusDays(7), null);
        Long futureFullId = createEventOn(today.plusDays(14), 1);
        linkToSameSchedule(pastId, todayId, futureOpenId, futureFullId);

        fillEventWithOtherHousehold(futureFullId);

        assertThat(eventService.getEventDetail(todayId, parentUser).isSeriesAttendanceAvailable()).isTrue();

        SeriesAttendResultDTO registered = eventService.registerParentSeries(parentUser, pastId, parentId);
        assertThat(registered.getAppliedCount()).isEqualTo(2);
        assertThat(registered.getSkippedFullCount()).isEqualTo(1);
        assertThat(countRegistered(todayId)).isEqualTo(1);
        assertThat(countRegistered(futureOpenId)).isEqualTo(1);
        assertThat(countRegistered(futureFullId)).isEqualTo(1);
        assertThat(countRegistered(pastId)).isZero();

        SeriesAttendResultDTO cancelled = eventService.cancelParentSeries(parentUser, todayId, parentId);
        assertThat(cancelled.getAppliedCount()).isEqualTo(2);
        assertThat(countRegistered(todayId)).isZero();
        assertThat(countRegistered(futureOpenId)).isZero();
        assertThat(countRegistered(futureFullId)).isEqualTo(1);
    }

    @Test
    void seriesRegisterIsIdempotentForAlreadyRegisteredEvents() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tokyo"));
        Long firstId = createEventOn(today, null);
        Long secondId = createEventOn(today.plusDays(7), null);
        linkToSameSchedule(firstId, secondId);

        eventService.registerParent(parentUser, firstId, parentId);
        SeriesAttendResultDTO result = eventService.registerParentSeries(parentUser, firstId, parentId);
        assertThat(result.getAppliedCount()).isEqualTo(1);
        assertThat(countRegistered(firstId)).isEqualTo(1);
        assertThat(countRegistered(secondId)).isEqualTo(1);
    }

    private EventForm sampleEventForm() {
        EventForm form = new EventForm();
        form.setTitle("運動会");
        form.setDescription("年次運動会です");
        form.setEventDate(LocalDate.of(2026, 8, 15));
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(LocalTime.of(12, 0));
        form.setCapacity(null);
        form.setParticipationUnit(ParticipationUnit.PARENT);
        return form;
    }

    private Long createActiveInstructor(String name) {
        Instant now = Instant.now();
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setNameKana("やまだ");
        instructor.setActive(true);
        instructor.setCreatedAt(now);
        instructor.setUpdatedAt(now);
        return instructorRepository.save(instructor).getId();
    }

    private Long createEventOn(LocalDate date, Integer capacity) {
        EventForm form = sampleEventForm();
        form.setEventDate(date);
        form.setCapacity(capacity);
        return eventService.createEvent(adminUser, form);
    }

    private void linkToSameSchedule(Long... eventIds) {
        Instant now = Instant.now();
        Schedule schedule = new Schedule();
        schedule.setTitle("週次クラス");
        schedule.setDescription("テスト用");
        schedule.setScheduleType(ScheduleType.WEEKLY);
        schedule.setCreatedByUserId(adminUser.getUser().getId());
        schedule.setCreatedAt(now);
        schedule.setUpdatedAt(now);
        Long scheduleId = scheduleRepository.save(schedule).getId();
        for (Long eventId : eventIds) {
            Event event = eventRepository.findById(eventId).orElseThrow();
            event.setScheduleId(scheduleId);
            eventRepository.save(event);
        }
    }

    private void fillEventWithOtherHousehold(Long eventId) {
        Instant now = Instant.now();
        Household otherHousehold = new Household();
        otherHousehold.setName("B家");
        otherHousehold.setNameKana("びーけ");
        otherHousehold.setCreatedAt(now);
        otherHousehold.setUpdatedAt(now);
        otherHousehold = householdRepository.save(otherHousehold);

        User otherParentUserEntity = new User();
        otherParentUserEntity.setEmail("other-series@test.local");
        otherParentUserEntity.setPasswordHash("hash");
        otherParentUserEntity.setRole(Role.PARENT);
        otherParentUserEntity.setHouseholdId(otherHousehold.getId());
        otherParentUserEntity.setCreatedAt(now);
        otherParentUserEntity.setUpdatedAt(now);
        otherParentUserEntity = userRepository.save(otherParentUserEntity);
        MinsukeUserDetails otherParentUser = new MinsukeUserDetails(otherParentUserEntity);

        ParentForm otherParentForm = new ParentForm();
        otherParentForm.setName("次郎");
        otherParentForm.setNameKana("じろう");
        familyService.createParent(otherParentUser, otherParentForm);
        Long otherParentId = parentRepository.findByHouseholdIdOrderByIdAsc(otherHousehold.getId()).get(0).getId();
        eventService.registerParent(otherParentUser, eventId, otherParentId);
    }

    private long countRegistered(Long eventId) {
        return attendanceRepository.countByEventIdAndStatus(eventId, AttendanceStatus.REGISTERED);
    }
}
