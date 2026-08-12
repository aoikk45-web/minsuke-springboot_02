package com.minsuke.event.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.domain.AttendanceStatus;
import com.minsuke.event.domain.ParticipantType;
import com.minsuke.event.dto.CalendarViewDTO;
import com.minsuke.event.dto.EventDetailDTO;
import com.minsuke.event.dto.EventForm;
import com.minsuke.event.entity.Event;
import com.minsuke.event.entity.EventAttendance;
import com.minsuke.event.exception.EventAccessDeniedException;
import com.minsuke.event.exception.EventCapacityFullException;
import com.minsuke.event.exception.EventNotFoundException;
import com.minsuke.event.repository.EventAttendanceRepository;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.family.entity.Child;
import com.minsuke.family.entity.Household;
import com.minsuke.family.entity.Parent;
import com.minsuke.family.repository.ChildRepository;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.family.repository.ParentRepository;
import com.minsuke.instructor.entity.Instructor;
import com.minsuke.instructor.repository.InstructorRepository;

@Service
public class EventService {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");
    private static final DateTimeFormatter MONTH_LABEL =
            DateTimeFormatter.ofPattern("yyyy年M月", Locale.JAPAN);

    private final EventRepository eventRepository;
    private final EventAttendanceRepository attendanceRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final HouseholdRepository householdRepository;
    private final InstructorRepository instructorRepository;

    public EventService(
            EventRepository eventRepository,
            EventAttendanceRepository attendanceRepository,
            ParentRepository parentRepository,
            ChildRepository childRepository,
            HouseholdRepository householdRepository,
            InstructorRepository instructorRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.householdRepository = householdRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public Long createEvent(MinsukeUserDetails user, EventForm form) {
        requireAdmin(user);
        validateTimeRange(form);
        Instant now = Instant.now();
        Event event = new Event();
        applyForm(event, form);
        event.setCreatedByUserId(user.getUser().getId());
        event.setCreatedAt(now);
        event.setUpdatedAt(now);
        return eventRepository.save(event).getId();
    }

    @Transactional
    public void updateEvent(MinsukeUserDetails user, Long eventId, EventForm form) {
        requireAdmin(user);
        validateTimeRange(form);
        Event event = findEventOrThrow(eventId);
        applyForm(event, form);
        event.setUpdatedAt(Instant.now());
        eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public EventForm toEventForm(Long eventId) {
        Event event = findEventOrThrow(eventId);
        EventForm form = new EventForm();
        form.setTitle(event.getTitle());
        form.setDescription(event.getDescription());
        form.setEventDate(event.getEventDate());
        form.setStartTime(event.getStartTime());
        form.setEndTime(event.getEndTime());
        form.setCapacity(event.getCapacity());
        form.setInstructorId(event.getInstructorId());
        return form;
    }

    @Transactional(readOnly = true)
    public EventDetailDTO getEventDetail(Long eventId, MinsukeUserDetails user) {
        Event event = findEventOrThrow(eventId);
        long registeredCount = countRegistered(eventId);
        EventDetailDTO dto = toDetail(event, registeredCount);
        dto.setRegisteredParticipants(buildRegisteredParticipants(eventId));
        if (user != null && user.getUser().getRole() == Role.PARENT) {
            dto.setParticipantOptions(buildParticipantOptions(event, user, registeredCount));
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public CalendarViewDTO buildCalendarView(Integer year, Integer month) {
        LocalDate today = LocalDate.now(ZONE);
        YearMonth target = resolveYearMonth(year, month, today);
        LocalDate start = target.atDay(1);
        LocalDate end = target.atEndOfMonth();

        List<Event> events = eventRepository.findByEventDateBetweenOrderByEventDateAscStartTimeAscIdAsc(start, end);
        Map<LocalDate, List<Event>> eventsByDate = events.stream()
                .collect(Collectors.groupingBy(Event::getEventDate));

        Map<Long, Long> registeredCounts = new HashMap<>();
        for (Event event : events) {
            registeredCounts.put(event.getId(), countRegistered(event.getId()));
        }

        CalendarViewDTO view = new CalendarViewDTO();
        view.setYear(target.getYear());
        view.setMonth(target.getMonthValue());
        view.setMonthLabel(target.format(MONTH_LABEL));

        YearMonth previous = target.minusMonths(1);
        YearMonth next = target.plusMonths(1);
        view.setPreviousYear(previous.getYear());
        view.setPreviousMonth(previous.getMonthValue());
        view.setNextYear(next.getYear());
        view.setNextMonth(next.getMonthValue());

        LocalDate gridStart = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate gridEnd = end.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        CalendarViewDTO.CalendarWeekDTO week = new CalendarViewDTO.CalendarWeekDTO();
        for (LocalDate date = gridStart; !date.isAfter(gridEnd); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY && !week.getDays().isEmpty()) {
                view.getWeeks().add(week);
                week = new CalendarViewDTO.CalendarWeekDTO();
            }
            week.getDays().add(toCalendarDay(date, target, today, eventsByDate, registeredCounts));
        }
        if (!week.getDays().isEmpty()) {
            view.getWeeks().add(week);
        }
        return view;
    }

    @Transactional
    public void registerParent(MinsukeUserDetails user, Long eventId, Long parentId) {
        Long householdId = requireParentHouseholdId(user);
        Event event = findEventOrThrow(eventId);
        Parent parent = parentRepository.findByIdAndHouseholdId(parentId, householdId)
                .orElseThrow(EventAccessDeniedException::new);
        registerParticipant(user, event, ParticipantType.PARENT, parent.getId(), null, householdId);
    }

    @Transactional
    public void registerChild(MinsukeUserDetails user, Long eventId, Long childId) {
        Long householdId = requireParentHouseholdId(user);
        Event event = findEventOrThrow(eventId);
        Child child = childRepository.findByIdAndHouseholdId(childId, householdId)
                .orElseThrow(EventAccessDeniedException::new);
        registerParticipant(user, event, ParticipantType.CHILD, null, child.getId(), householdId);
    }

    @Transactional
    public void cancelParent(MinsukeUserDetails user, Long eventId, Long parentId) {
        Long householdId = requireParentHouseholdId(user);
        parentRepository.findByIdAndHouseholdId(parentId, householdId)
                .orElseThrow(EventAccessDeniedException::new);
        cancelParticipant(eventId, parentId, null);
    }

    @Transactional
    public void cancelChild(MinsukeUserDetails user, Long eventId, Long childId) {
        Long householdId = requireParentHouseholdId(user);
        childRepository.findByIdAndHouseholdId(childId, householdId)
                .orElseThrow(EventAccessDeniedException::new);
        cancelParticipant(eventId, null, childId);
    }

    private void registerParticipant(
            MinsukeUserDetails user,
            Event event,
            ParticipantType type,
            Long parentId,
            Long childId,
            Long householdId) {
        ensureCapacityAvailable(event);
        Instant now = Instant.now();
        OptionalAttendance existing = findExistingAttendance(event.getId(), parentId, childId);
        if (existing.isRegistered()) {
            return;
        }
        if (existing.attendance() != null) {
            EventAttendance attendance = existing.attendance();
            attendance.setStatus(AttendanceStatus.REGISTERED);
            attendance.setUpdatedAt(now);
            attendanceRepository.save(attendance);
            return;
        }
        EventAttendance attendance = new EventAttendance();
        attendance.setEventId(event.getId());
        attendance.setParticipantType(type);
        attendance.setParentId(parentId);
        attendance.setChildId(childId);
        attendance.setHouseholdId(householdId);
        attendance.setRegisteredByUserId(user.getUser().getId());
        attendance.setStatus(AttendanceStatus.REGISTERED);
        attendance.setRegisteredAt(now);
        attendance.setUpdatedAt(now);
        attendanceRepository.save(attendance);
    }

    private void cancelParticipant(Long eventId, Long parentId, Long childId) {
        OptionalAttendance existing = findExistingAttendance(eventId, parentId, childId);
        if (!existing.isRegistered() || existing.attendance() == null) {
            return;
        }
        EventAttendance attendance = existing.attendance();
        attendance.setStatus(AttendanceStatus.CANCELLED);
        attendance.setUpdatedAt(Instant.now());
        attendanceRepository.save(attendance);
    }

    private OptionalAttendance findExistingAttendance(Long eventId, Long parentId, Long childId) {
        EventAttendance attendance;
        if (parentId != null) {
            attendance = attendanceRepository.findFirstByEventIdAndParentIdOrderByIdDesc(eventId, parentId)
                    .orElse(null);
        } else {
            attendance = attendanceRepository.findFirstByEventIdAndChildIdOrderByIdDesc(eventId, childId)
                    .orElse(null);
        }
        boolean registered = attendance != null && attendance.getStatus() == AttendanceStatus.REGISTERED;
        return new OptionalAttendance(attendance, registered);
    }

    private void ensureCapacityAvailable(Event event) {
        if (event.getCapacity() == null) {
            return;
        }
        long registered = countRegistered(event.getId());
        if (registered >= event.getCapacity()) {
            throw new EventCapacityFullException();
        }
    }

    private long countRegistered(Long eventId) {
        return attendanceRepository.countByEventIdAndStatus(eventId, AttendanceStatus.REGISTERED);
    }

    private List<EventDetailDTO.ParticipantOptionDTO> buildParticipantOptions(
            Event event, MinsukeUserDetails user, long registeredCount) {
        Long householdId = user.getHouseholdId();
        boolean eventFull = event.getCapacity() != null && registeredCount >= event.getCapacity();
        List<EventDetailDTO.ParticipantOptionDTO> options = new ArrayList<>();

        for (Parent parent : parentRepository.findByHouseholdIdOrderByIdAsc(householdId)) {
            boolean registered = isRegistered(event.getId(), parent.getId(), null);
            options.add(buildOption(
                    "parent-" + parent.getId(),
                    EventDetailDTO.ParticipantType.PARENT,
                    parent.getId(),
                    null,
                    parent.getName(),
                    registered,
                    !registered && !eventFull));
        }
        for (Child child : childRepository.findByHouseholdIdOrderByIdAsc(householdId)) {
            boolean registered = isRegistered(event.getId(), null, child.getId());
            options.add(buildOption(
                    "child-" + child.getId(),
                    EventDetailDTO.ParticipantType.CHILD,
                    null,
                    child.getId(),
                    child.getName(),
                    registered,
                    !registered && !eventFull));
        }
        return options;
    }

    private EventDetailDTO.ParticipantOptionDTO buildOption(
            String key,
            EventDetailDTO.ParticipantType type,
            Long parentId,
            Long childId,
            String name,
            boolean registered,
            boolean canRegister) {
        EventDetailDTO.ParticipantOptionDTO option = new EventDetailDTO.ParticipantOptionDTO();
        option.setKey(key);
        option.setType(type);
        option.setParentId(parentId);
        option.setChildId(childId);
        option.setName(name);
        option.setRegistered(registered);
        option.setCanRegister(canRegister);
        return option;
    }

    private boolean isRegistered(Long eventId, Long parentId, Long childId) {
        return findExistingAttendance(eventId, parentId, childId).isRegistered();
    }

    private List<EventDetailDTO.RegisteredParticipantDTO> buildRegisteredParticipants(Long eventId) {
        Map<Long, String> householdNames = householdRepository.findAll().stream()
                .collect(Collectors.toMap(Household::getId, Household::getName));
        Map<Long, Parent> parents = parentRepository.findAll().stream()
                .collect(Collectors.toMap(Parent::getId, p -> p));
        Map<Long, Child> children = childRepository.findAll().stream()
                .collect(Collectors.toMap(Child::getId, c -> c));

        List<EventDetailDTO.RegisteredParticipantDTO> result = new ArrayList<>();
        for (EventAttendance attendance : attendanceRepository.findByEventIdAndStatusOrderByIdAsc(
                eventId, AttendanceStatus.REGISTERED)) {
            EventDetailDTO.RegisteredParticipantDTO row = new EventDetailDTO.RegisteredParticipantDTO();
            row.setHouseholdName(householdNames.getOrDefault(attendance.getHouseholdId(), "—"));
            if (attendance.getParticipantType() == ParticipantType.PARENT) {
                Parent parent = parents.get(attendance.getParentId());
                row.setName(parent != null ? parent.getName() : "—");
                row.setTypeLabel("保護者");
            } else {
                Child child = children.get(attendance.getChildId());
                row.setName(child != null ? child.getName() : "—");
                row.setTypeLabel("子ども");
            }
            result.add(row);
        }
        return result;
    }

    private CalendarViewDTO.CalendarDayDTO toCalendarDay(
            LocalDate date,
            YearMonth target,
            LocalDate today,
            Map<LocalDate, List<Event>> eventsByDate,
            Map<Long, Long> registeredCounts) {
        CalendarViewDTO.CalendarDayDTO day = new CalendarViewDTO.CalendarDayDTO();
        day.setDate(date);
        day.setDayOfMonth(date.getDayOfMonth());
        day.setCurrentMonth(YearMonth.from(date).equals(target));
        day.setToday(date.equals(today));
        List<Event> dayEvents = eventsByDate.getOrDefault(date, List.of());
        for (Event event : dayEvents) {
            CalendarViewDTO.CalendarEventDTO item = new CalendarViewDTO.CalendarEventDTO();
            item.setId(event.getId());
            item.setTitle(event.getTitle());
            long registered = registeredCounts.getOrDefault(event.getId(), 0L);
            item.setFull(event.getCapacity() != null && registered >= event.getCapacity());
            day.getEvents().add(item);
        }
        return day;
    }

    private EventDetailDTO toDetail(Event event, long registeredCount) {
        EventDetailDTO dto = new EventDetailDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setCapacity(event.getCapacity());
        dto.setInstructorId(event.getInstructorId());
        if (event.getInstructorId() != null) {
            instructorRepository.findById(event.getInstructorId())
                    .ifPresent(instructor -> dto.setInstructorName(instructor.getName()));
        }
        dto.setRegisteredCount(registeredCount);
        dto.setUnlimitedCapacity(event.getCapacity() == null);
        dto.setFull(event.getCapacity() != null && registeredCount >= event.getCapacity());
        return dto;
    }

    private void applyForm(Event event, EventForm form) {
        event.setTitle(form.getTitle());
        event.setDescription(form.getDescription());
        event.setEventDate(form.getEventDate());
        event.setStartTime(form.getStartTime());
        event.setEndTime(form.getEndTime());
        event.setCapacity(form.getCapacity());
        event.setInstructorId(resolveInstructorId(form.getInstructorId()));
    }

    private Long resolveInstructorId(Long instructorId) {
        if (instructorId == null) {
            return null;
        }
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("指定された講師が見つかりません"));
        if (!instructor.isActive()) {
            throw new IllegalArgumentException("無効な講師は担当に設定できません");
        }
        return instructor.getId();
    }

    private void validateTimeRange(EventForm form) {
        if (form.getStartTime() != null && form.getEndTime() != null
                && form.getStartTime().isAfter(form.getEndTime())) {
            throw new IllegalArgumentException("終了時刻は開始時刻以降にしてください");
        }
    }

    private YearMonth resolveYearMonth(Integer year, Integer month, LocalDate today) {
        if (year == null || month == null) {
            return YearMonth.from(today);
        }
        return YearMonth.of(year, month);
    }

    private Event findEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    private void requireAdmin(MinsukeUserDetails user) {
        if (user.getUser().getRole() != Role.ADMIN) {
            throw new EventAccessDeniedException();
        }
    }

    private Long requireParentHouseholdId(MinsukeUserDetails user) {
        if (user.getUser().getRole() != Role.PARENT) {
            throw new EventAccessDeniedException();
        }
        Long householdId = user.getHouseholdId();
        if (householdId == null) {
            throw new EventAccessDeniedException();
        }
        return householdId;
    }

    private record OptionalAttendance(EventAttendance attendance, boolean registered) {
        boolean isRegistered() {
            return registered;
        }
    }
}
