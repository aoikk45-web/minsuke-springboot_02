package com.minsuke.schedule.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.entity.Event;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.instructor.entity.Instructor;
import com.minsuke.instructor.repository.InstructorRepository;
import com.minsuke.schedule.domain.ScheduleType;
import com.minsuke.schedule.dto.ScheduleCardDTO;
import com.minsuke.schedule.dto.ScheduleDetailDTO;
import com.minsuke.schedule.dto.ScheduleForm;
import com.minsuke.schedule.dto.ScheduleGenerateResultDTO;
import com.minsuke.schedule.entity.Schedule;
import com.minsuke.schedule.exception.ScheduleAccessDeniedException;
import com.minsuke.schedule.exception.ScheduleNotFoundException;
import com.minsuke.schedule.repository.ScheduleRepository;

@Service
public class ScheduleService {

    public static final int DEFAULT_GENERATE_WEEKS = 4;

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");

    private static final String[] DAY_LABELS = {
            "", "月", "火", "水", "木", "金", "土", "日"
    };

    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;
    private final InstructorRepository instructorRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            EventRepository eventRepository,
            InstructorRepository instructorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.eventRepository = eventRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleCardDTO> list(MinsukeUserDetails user) {
        requireAdmin(user);
        return scheduleRepository.findAllByOrderByUpdatedAtDescIdDesc().stream()
                .map(this::toCard)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleDetailDTO getDetail(Long id, MinsukeUserDetails user) {
        requireAdmin(user);
        Schedule schedule = findOrThrow(id);
        ScheduleDetailDTO dto = toDetail(schedule);
        eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(id).forEach(event -> {
            ScheduleDetailDTO.GeneratedEventDTO row = new ScheduleDetailDTO.GeneratedEventDTO();
            row.setId(event.getId());
            row.setEventDate(event.getEventDate());
            row.setStartTime(event.getStartTime());
            row.setTitle(event.getTitle());
            dto.getGeneratedEvents().add(row);
        });
        return dto;
    }

    @Transactional(readOnly = true)
    public ScheduleForm toForm(Long id) {
        return toForm(findOrThrow(id));
    }

    @Transactional
    public Long create(MinsukeUserDetails user, ScheduleForm form) {
        requireAdmin(user);
        validateForm(form);
        Instant now = Instant.now();
        Schedule schedule = new Schedule();
        applyForm(schedule, form);
        schedule.setCreatedByUserId(user.getUser().getId());
        schedule.setCreatedAt(now);
        schedule.setUpdatedAt(now);
        return scheduleRepository.save(schedule).getId();
    }

    @Transactional
    public void update(MinsukeUserDetails user, Long id, ScheduleForm form) {
        requireAdmin(user);
        validateForm(form);
        Schedule schedule = findOrThrow(id);
        applyForm(schedule, form);
        schedule.setUpdatedAt(Instant.now());
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(MinsukeUserDetails user, Long id) {
        requireAdmin(user);
        scheduleRepository.delete(findOrThrow(id));
    }

    @Transactional
    public ScheduleGenerateResultDTO generateEvents(MinsukeUserDetails user, Long scheduleId, Integer weeks) {
        requireAdmin(user);
        Schedule schedule = findOrThrow(scheduleId);
        if (!schedule.isActive()) {
            throw new IllegalArgumentException("無効なスケジュールからはイベントを生成できません");
        }
        int horizonWeeks = weeks != null && weeks > 0 ? weeks : DEFAULT_GENERATE_WEEKS;
        List<LocalDate> dates = resolveGenerationDates(schedule, horizonWeeks);
        Instant now = Instant.now();
        int created = 0;
        int skipped = 0;
        for (LocalDate date : dates) {
            if (eventRepository.existsByScheduleIdAndEventDate(scheduleId, date)) {
                skipped++;
                continue;
            }
            Event event = new Event();
            event.setTitle(schedule.getTitle());
            event.setDescription(schedule.getDescription());
            event.setEventDate(date);
            event.setStartTime(schedule.getStartTime());
            event.setEndTime(schedule.getEndTime());
            event.setCapacity(schedule.getCapacity());
            event.setInstructorId(schedule.getInstructorId());
            event.setScheduleId(scheduleId);
            event.setCreatedByUserId(user.getUser().getId());
            event.setCreatedAt(now);
            event.setUpdatedAt(now);
            eventRepository.save(event);
            created++;
        }
        ScheduleGenerateResultDTO result = new ScheduleGenerateResultDTO();
        result.setCreatedCount(created);
        result.setSkippedCount(skipped);
        return result;
    }

    private List<LocalDate> resolveGenerationDates(Schedule schedule, int horizonWeeks) {
        LocalDate today = LocalDate.now(ZONE);
        List<LocalDate> dates = new ArrayList<>();
        if (schedule.getScheduleType() == ScheduleType.ONE_OFF) {
            LocalDate date = schedule.getOneOffDate();
            if (!date.isBefore(today)) {
                dates.add(date);
            }
            return dates;
        }
        LocalDate start = schedule.getValidFrom() != null && schedule.getValidFrom().isAfter(today)
                ? schedule.getValidFrom() : today;
        LocalDate end = start.plusWeeks(horizonWeeks);
        if (schedule.getValidUntil() != null && schedule.getValidUntil().isBefore(end)) {
            end = schedule.getValidUntil();
        }
        Set<DayOfWeek> targets = schedule.getDaysOfWeek().stream()
                .filter(day -> day != null && day >= 1 && day <= 7)
                .map(DayOfWeek::of)
                .collect(Collectors.toSet());
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (targets.contains(date.getDayOfWeek())) {
                dates.add(date);
            }
        }
        return dates;
    }

    private void validateForm(ScheduleForm form) {
        if (form.getStartTime() != null && form.getEndTime() != null
                && form.getStartTime().isAfter(form.getEndTime())) {
            throw new IllegalArgumentException("終了時刻は開始時刻以降にしてください");
        }
        if (form.getScheduleType() == ScheduleType.ONE_OFF) {
            if (form.getOneOffDate() == null) {
                throw new IllegalArgumentException("単発スケジュールの開催日を入力してください");
            }
        } else if (form.getScheduleType() == ScheduleType.WEEKLY) {
            if (normalizeDays(form.getDaysOfWeek()).isEmpty()) {
                throw new IllegalArgumentException("曜日を1つ以上選択してください");
            }
            if (form.getValidFrom() != null && form.getValidUntil() != null
                    && form.getValidFrom().isAfter(form.getValidUntil())) {
                throw new IllegalArgumentException("適用終了日は適用開始日以降にしてください");
            }
        }
        resolveInstructorId(form.getInstructorId());
    }

    private void applyForm(Schedule schedule, ScheduleForm form) {
        schedule.setTitle(form.getTitle().trim());
        schedule.setDescription(form.getDescription().trim());
        schedule.setScheduleType(form.getScheduleType());
        schedule.setStartTime(form.getStartTime());
        schedule.setEndTime(form.getEndTime());
        schedule.setCapacity(form.getCapacity());
        schedule.setInstructorId(resolveInstructorId(form.getInstructorId()));
        schedule.setActive(form.isActive());
        if (form.getScheduleType() == ScheduleType.ONE_OFF) {
            schedule.setOneOffDate(form.getOneOffDate());
            schedule.getDaysOfWeek().clear();
            schedule.setValidFrom(null);
            schedule.setValidUntil(null);
        } else {
            schedule.getDaysOfWeek().clear();
            schedule.getDaysOfWeek().addAll(normalizeDays(form.getDaysOfWeek()));
            schedule.setValidFrom(form.getValidFrom());
            schedule.setValidUntil(form.getValidUntil());
            schedule.setOneOffDate(null);
        }
    }

    private Set<Integer> normalizeDays(List<Integer> days) {
        if (days == null) {
            return Set.of();
        }
        return days.stream()
                .filter(day -> day != null && day >= 1 && day <= 7)
                .collect(Collectors.toCollection(LinkedHashSet::new));
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

    private Schedule findOrThrow(Long id) {
        return scheduleRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
    }

    private void requireAdmin(MinsukeUserDetails user) {
        if (user == null || user.getUser().getRole() != Role.ADMIN) {
            throw new ScheduleAccessDeniedException();
        }
    }

    private ScheduleForm toForm(Schedule schedule) {
        ScheduleForm form = new ScheduleForm();
        form.setTitle(schedule.getTitle());
        form.setDescription(schedule.getDescription());
        form.setScheduleType(schedule.getScheduleType());
        form.setDaysOfWeek(schedule.getDaysOfWeek().stream().sorted().toList());
        form.setOneOffDate(schedule.getOneOffDate());
        form.setValidFrom(schedule.getValidFrom());
        form.setValidUntil(schedule.getValidUntil());
        form.setStartTime(schedule.getStartTime());
        form.setEndTime(schedule.getEndTime());
        form.setCapacity(schedule.getCapacity());
        form.setInstructorId(schedule.getInstructorId());
        form.setActive(schedule.isActive());
        return form;
    }

    private ScheduleCardDTO toCard(Schedule schedule) {
        ScheduleCardDTO dto = new ScheduleCardDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setScheduleType(schedule.getScheduleType());
        dto.setScheduleTypeLabel(typeLabel(schedule.getScheduleType()));
        dto.setActive(schedule.isActive());
        dto.setGeneratedEventCount(
                eventRepository.findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(schedule.getId()).size());
        return dto;
    }

    private ScheduleDetailDTO toDetail(Schedule schedule) {
        ScheduleDetailDTO dto = new ScheduleDetailDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setScheduleType(schedule.getScheduleType());
        dto.setScheduleTypeLabel(typeLabel(schedule.getScheduleType()));
        List<Integer> days = schedule.getDaysOfWeek().stream().sorted().toList();
        dto.setDaysOfWeek(days);
        dto.setDaysOfWeekLabel(dayLabels(days));
        dto.setOneOffDate(schedule.getOneOffDate());
        dto.setValidFrom(schedule.getValidFrom());
        dto.setValidUntil(schedule.getValidUntil());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setCapacity(schedule.getCapacity());
        dto.setInstructorId(schedule.getInstructorId());
        if (schedule.getInstructorId() != null) {
            instructorRepository.findById(schedule.getInstructorId())
                    .ifPresent(i -> dto.setInstructorName(i.getName()));
        }
        dto.setActive(schedule.isActive());
        return dto;
    }

    private String dayLabels(List<Integer> days) {
        if (days == null || days.isEmpty()) {
            return null;
        }
        return days.stream()
                .filter(day -> day >= 1 && day <= 7)
                .map(day -> DAY_LABELS[day] + "曜日")
                .collect(Collectors.joining("・"));
    }

    private String typeLabel(ScheduleType type) {
        return type == ScheduleType.ONE_OFF ? "単発" : "毎週";
    }
}
