package com.minsuke.event.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.domain.AttendanceStatus;
import com.minsuke.event.dto.EventFillRowDTO;
import com.minsuke.event.dto.HouseholdParticipationRateDTO;
import com.minsuke.event.dto.ScheduleParticipationViewDTO;
import com.minsuke.event.entity.Event;
import com.minsuke.event.entity.EventAttendance;
import com.minsuke.event.exception.EventAccessDeniedException;
import com.minsuke.event.repository.EventAttendanceRepository;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.family.entity.Household;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.schedule.entity.Schedule;
import com.minsuke.schedule.exception.ScheduleNotFoundException;
import com.minsuke.schedule.repository.ScheduleRepository;

@Service
public class AdminParticipationService {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");

    private final EventRepository eventRepository;
    private final EventAttendanceRepository attendanceRepository;
    private final HouseholdRepository householdRepository;
    private final ScheduleRepository scheduleRepository;

    public AdminParticipationService(
            EventRepository eventRepository,
            EventAttendanceRepository attendanceRepository,
            HouseholdRepository householdRepository,
            ScheduleRepository scheduleRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
        this.householdRepository = householdRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional(readOnly = true)
    public ScheduleParticipationViewDTO listScheduleHouseholdRates(
            MinsukeUserDetails user, Long scheduleId, YearMonth monthFilter) {
        requireAdmin(user);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        List<Event> events = eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(scheduleId);
        if (monthFilter != null) {
            LocalDate start = monthFilter.atDay(1);
            LocalDate end = monthFilter.atEndOfMonth();
            events = events.stream()
                    .filter(event -> !event.getEventDate().isBefore(start)
                            && !event.getEventDate().isAfter(end))
                    .collect(Collectors.toList());
        }

        int totalEvents = events.size();
        Set<Long> eventIds = events.stream().map(Event::getId).collect(Collectors.toSet());
        Map<Long, Set<Long>> attendedEventIdsByHousehold = new HashMap<>();
        if (!eventIds.isEmpty()) {
            for (EventAttendance attendance : attendanceRepository
                    .findByEventIdInAndStatus(eventIds, AttendanceStatus.REGISTERED)) {
                attendedEventIdsByHousehold
                        .computeIfAbsent(attendance.getHouseholdId(), ignored -> new HashSet<>())
                        .add(attendance.getEventId());
            }
        }

        List<HouseholdParticipationRateDTO> rows = new ArrayList<>();
        for (Household household : householdRepository.findAll()) {
            HouseholdParticipationRateDTO row = new HouseholdParticipationRateDTO();
            row.setHouseholdId(household.getId());
            row.setHouseholdName(household.getName());
            int attended = attendedEventIdsByHousehold
                    .getOrDefault(household.getId(), Set.of())
                    .size();
            row.setAttendedCount(attended);
            row.setTotalEvents(totalEvents);
            row.setRatePercent(totalEvents == 0 ? 0 : (int) Math.round(attended * 100.0 / totalEvents));
            rows.add(row);
        }
        rows.sort(Comparator
                .comparingInt(HouseholdParticipationRateDTO::getRatePercent)
                .thenComparing(HouseholdParticipationRateDTO::getHouseholdName, Comparator.nullsLast(String::compareTo))
                .thenComparing(HouseholdParticipationRateDTO::getHouseholdId));

        ScheduleParticipationViewDTO view = new ScheduleParticipationViewDTO();
        view.setScheduleId(schedule.getId());
        view.setScheduleTitle(schedule.getTitle());
        view.setTotalEvents(totalEvents);
        view.setFilterMonth(monthFilter);
        view.setHouseholds(rows);
        return view;
    }

    @Transactional(readOnly = true)
    public List<EventFillRowDTO> listMonthlyEventFills(MinsukeUserDetails user, YearMonth month) {
        requireAdmin(user);
        YearMonth target = month != null ? month : YearMonth.now(ZONE);
        LocalDate start = target.atDay(1);
        LocalDate end = target.atEndOfMonth();
        List<Event> events = eventRepository
                .findByEventDateBetweenOrderByEventDateAscStartTimeAscIdAsc(start, end);

        Map<Long, String> scheduleTitles = scheduleRepository.findAll().stream()
                .collect(Collectors.toMap(Schedule::getId, Schedule::getTitle));

        List<EventFillRowDTO> rows = new ArrayList<>();
        for (Event event : events) {
            long registered = attendanceRepository.countByEventIdAndStatus(
                    event.getId(), AttendanceStatus.REGISTERED);
            EventFillRowDTO row = new EventFillRowDTO();
            row.setEventId(event.getId());
            row.setEventTitle(event.getTitle());
            row.setEventDate(event.getEventDate());
            row.setStartTime(event.getStartTime());
            row.setCapacity(event.getCapacity());
            row.setRegisteredCount(registered);
            if (event.getCapacity() != null) {
                row.setRemaining(Math.max(0, event.getCapacity() - registered));
                row.setFillPercent((int) Math.round(registered * 100.0 / event.getCapacity()));
            }
            if (event.getScheduleId() != null) {
                row.setScheduleTitle(scheduleTitles.get(event.getScheduleId()));
            }
            rows.add(row);
        }
        return rows;
    }

    private void requireAdmin(MinsukeUserDetails user) {
        if (user == null || user.getUser().getRole() != Role.ADMIN) {
            throw new EventAccessDeniedException();
        }
    }
}
