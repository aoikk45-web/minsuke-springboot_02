package com.minsuke.instructor.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.entity.Event;
import com.minsuke.event.repository.EventRepository;
import com.minsuke.instructor.dto.InstructorCardDTO;
import com.minsuke.instructor.dto.InstructorDetailDTO;
import com.minsuke.instructor.dto.InstructorForm;
import com.minsuke.instructor.dto.InstructorWorkloadDTO;
import com.minsuke.instructor.entity.Instructor;
import com.minsuke.instructor.exception.InstructorAccessDeniedException;
import com.minsuke.instructor.exception.InstructorNotFoundException;
import com.minsuke.instructor.repository.InstructorRepository;

@Service
public class InstructorService {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");
    private static final DateTimeFormatter MONTH_LABEL =
            DateTimeFormatter.ofPattern("yyyy年M月", Locale.JAPAN);

    private final InstructorRepository instructorRepository;
    private final EventRepository eventRepository;

    public InstructorService(InstructorRepository instructorRepository, EventRepository eventRepository) {
        this.instructorRepository = instructorRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<InstructorCardDTO> listInstructors(MinsukeUserDetails user) {
        List<Instructor> instructors = isAdmin(user)
                ? instructorRepository.findAllByOrderByNameKanaAscIdAsc()
                : instructorRepository.findByActiveTrueOrderByNameKanaAscIdAsc();
        return instructors.stream().map(this::toCard).toList();
    }

    @Transactional(readOnly = true)
    public List<InstructorCardDTO> listActiveInstructors() {
        return instructorRepository.findByActiveTrueOrderByNameKanaAscIdAsc().stream()
                .map(this::toCard)
                .toList();
    }

    @Transactional(readOnly = true)
    public InstructorDetailDTO getInstructor(Long id, MinsukeUserDetails user) {
        Instructor instructor = findOrThrow(id);
        if (!isAdmin(user) && !instructor.isActive()) {
            throw new InstructorNotFoundException();
        }
        InstructorDetailDTO dto = toDetail(instructor);
        dto.setWorkload(buildWorkload(id));
        return dto;
    }

    @Transactional(readOnly = true)
    public InstructorForm toForm(Long id) {
        return toForm(findOrThrow(id));
    }

    @Transactional
    public Long create(MinsukeUserDetails user, InstructorForm form) {
        requireAdmin(user);
        Instant now = Instant.now();
        Instructor instructor = new Instructor();
        applyForm(instructor, form);
        instructor.setCreatedAt(now);
        instructor.setUpdatedAt(now);
        return instructorRepository.save(instructor).getId();
    }

    @Transactional
    public void update(MinsukeUserDetails user, Long id, InstructorForm form) {
        requireAdmin(user);
        Instructor instructor = findOrThrow(id);
        applyForm(instructor, form);
        instructor.setUpdatedAt(Instant.now());
        instructorRepository.save(instructor);
    }

    @Transactional
    public void deactivate(MinsukeUserDetails user, Long id) {
        requireAdmin(user);
        Instructor instructor = findOrThrow(id);
        instructor.setActive(false);
        instructor.setUpdatedAt(Instant.now());
        instructorRepository.save(instructor);
    }

    @Transactional
    public void delete(MinsukeUserDetails user, Long id) {
        requireAdmin(user);
        Instructor instructor = findOrThrow(id);
        // events.instructor_id is ON DELETE SET NULL
        instructorRepository.delete(instructor);
    }

    private InstructorWorkloadDTO buildWorkload(Long instructorId) {
        LocalDate today = LocalDate.now(ZONE);
        List<Event> upcoming = eventRepository
                .findByInstructorIdAndEventDateGreaterThanEqualOrderByEventDateAscStartTimeAscIdAsc(
                        instructorId, today);
        List<Event> allAssigned = eventRepository
                .findByInstructorIdOrderByEventDateAscStartTimeAscIdAsc(instructorId);

        InstructorWorkloadDTO workload = new InstructorWorkloadDTO();
        workload.setTotalAssignedCount(allAssigned.size());
        for (Event event : upcoming) {
            InstructorWorkloadDTO.AssignedEventDTO row = new InstructorWorkloadDTO.AssignedEventDTO();
            row.setId(event.getId());
            row.setTitle(event.getTitle());
            row.setEventDate(event.getEventDate());
            row.setStartTime(event.getStartTime());
            row.setEndTime(event.getEndTime());
            workload.getUpcomingEvents().add(row);
        }

        Map<YearMonth, Long> counts = allAssigned.stream()
                .collect(Collectors.groupingBy(e -> YearMonth.from(e.getEventDate()), Collectors.counting()));
        counts.entrySet().stream()
                .sorted(Map.Entry.<YearMonth, Long>comparingByKey().reversed())
                .forEach(entry -> {
                    InstructorWorkloadDTO.MonthlyCountDTO row = new InstructorWorkloadDTO.MonthlyCountDTO();
                    row.setYearMonthLabel(entry.getKey().format(MONTH_LABEL));
                    row.setCount(entry.getValue());
                    workload.getMonthlyCounts().add(row);
                });
        return workload;
    }

    private Instructor findOrThrow(Long id) {
        return instructorRepository.findById(id).orElseThrow(InstructorNotFoundException::new);
    }

    private void requireAdmin(MinsukeUserDetails user) {
        if (!isAdmin(user)) {
            throw new InstructorAccessDeniedException();
        }
    }

    private boolean isAdmin(MinsukeUserDetails user) {
        return user != null && user.getUser().getRole() == Role.ADMIN;
    }

    private void applyForm(Instructor instructor, InstructorForm form) {
        instructor.setName(form.getName().trim());
        instructor.setNameKana(form.getNameKana().trim());
        instructor.setEmail(blankToNull(form.getEmail()));
        instructor.setPhone(blankToNull(form.getPhone()));
        instructor.setNotes(blankToNull(form.getNotes()));
        instructor.setActive(form.isActive());
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private InstructorCardDTO toCard(Instructor instructor) {
        InstructorCardDTO dto = new InstructorCardDTO();
        dto.setId(instructor.getId());
        dto.setName(instructor.getName());
        dto.setNameKana(instructor.getNameKana());
        dto.setEmail(instructor.getEmail());
        dto.setPhone(instructor.getPhone());
        dto.setActive(instructor.isActive());
        return dto;
    }

    private InstructorDetailDTO toDetail(Instructor instructor) {
        InstructorDetailDTO dto = new InstructorDetailDTO();
        dto.setId(instructor.getId());
        dto.setName(instructor.getName());
        dto.setNameKana(instructor.getNameKana());
        dto.setEmail(instructor.getEmail());
        dto.setPhone(instructor.getPhone());
        dto.setNotes(instructor.getNotes());
        dto.setActive(instructor.isActive());
        return dto;
    }

    private InstructorForm toForm(Instructor instructor) {
        InstructorForm form = new InstructorForm();
        form.setName(instructor.getName());
        form.setNameKana(instructor.getNameKana());
        form.setEmail(instructor.getEmail());
        form.setPhone(instructor.getPhone());
        form.setNotes(instructor.getNotes());
        form.setActive(instructor.isActive());
        return form;
    }
}
