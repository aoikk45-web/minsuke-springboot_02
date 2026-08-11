package com.minsuke.instructor.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.instructor.dto.InstructorCardDTO;
import com.minsuke.instructor.dto.InstructorDetailDTO;
import com.minsuke.instructor.dto.InstructorForm;
import com.minsuke.instructor.entity.Instructor;
import com.minsuke.instructor.exception.InstructorAccessDeniedException;
import com.minsuke.instructor.exception.InstructorNotFoundException;
import com.minsuke.instructor.repository.InstructorRepository;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Transactional(readOnly = true)
    public List<InstructorCardDTO> listInstructors(MinsukeUserDetails user) {
        List<Instructor> instructors = isAdmin(user)
                ? instructorRepository.findAllByOrderByNameKanaAscIdAsc()
                : instructorRepository.findByActiveTrueOrderByNameKanaAscIdAsc();
        return instructors.stream().map(this::toCard).toList();
    }

    @Transactional(readOnly = true)
    public InstructorDetailDTO getInstructor(Long id, MinsukeUserDetails user) {
        Instructor instructor = findOrThrow(id);
        if (!isAdmin(user) && !instructor.isActive()) {
            throw new InstructorNotFoundException();
        }
        return toDetail(instructor);
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
        // Loop 09 で割当 FK が入ったら参照チェックを追加する
        instructorRepository.delete(instructor);
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
