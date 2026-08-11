package com.minsuke.family.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.auth.domain.Role;
import com.minsuke.auth.repository.UserRepository;
import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.family.dto.ChildForm;
import com.minsuke.family.dto.HouseholdCardDTO;
import com.minsuke.family.dto.HouseholdDetailDTO;
import com.minsuke.family.dto.HouseholdForm;
import com.minsuke.family.dto.ParentForm;
import com.minsuke.family.entity.Child;
import com.minsuke.family.entity.Household;
import com.minsuke.family.entity.Parent;
import com.minsuke.family.exception.FamilyAccessDeniedException;
import com.minsuke.family.exception.FamilyNotFoundException;
import com.minsuke.family.repository.ChildRepository;
import com.minsuke.family.repository.HouseholdRepository;
import com.minsuke.family.repository.ParentRepository;

@Service
public class FamilyService {

    private final HouseholdRepository householdRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final UserRepository userRepository;

    public FamilyService(
            HouseholdRepository householdRepository,
            ParentRepository parentRepository,
            ChildRepository childRepository,
            UserRepository userRepository) {
        this.householdRepository = householdRepository;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<HouseholdCardDTO> listHouseholds() {
        return householdRepository.findAll().stream()
                .sorted((a, b) -> a.getNameKana().compareTo(b.getNameKana()))
                .map(this::toCard)
                .toList();
    }

    @Transactional(readOnly = true)
    public HouseholdDetailDTO getHouseholdDetail(Long householdId) {
        Household household = findHouseholdOrThrow(householdId);
        return toDetail(household);
    }

    @Transactional(readOnly = true)
    public HouseholdDetailDTO getMyHousehold(MinsukeUserDetails user) {
        Long householdId = requireParentHouseholdId(user);
        return getHouseholdDetail(householdId);
    }

    @Transactional
    public void updateMyHousehold(MinsukeUserDetails user, HouseholdForm form) {
        Long householdId = requireParentHouseholdId(user);
        Household household = findHouseholdOrThrow(householdId);
        applyHouseholdForm(household, form);
        household.setUpdatedAt(Instant.now());
        householdRepository.save(household);
    }

    @Transactional
    public void createParent(MinsukeUserDetails user, ParentForm form) {
        Long householdId = requireParentHouseholdId(user);
        Instant now = Instant.now();
        Parent parent = new Parent();
        parent.setHouseholdId(householdId);
        applyParentForm(parent, form);
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        parentRepository.save(parent);
    }

    @Transactional
    public void updateParent(MinsukeUserDetails user, Long parentId, ParentForm form) {
        Long householdId = requireParentHouseholdId(user);
        Parent parent = parentRepository.findByIdAndHouseholdId(parentId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        applyParentForm(parent, form);
        parent.setUpdatedAt(Instant.now());
        parentRepository.save(parent);
    }

    @Transactional
    public void deleteParent(MinsukeUserDetails user, Long parentId) {
        Long householdId = requireParentHouseholdId(user);
        Parent parent = parentRepository.findByIdAndHouseholdId(parentId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        parentRepository.delete(parent);
    }

    @Transactional
    public void createChild(MinsukeUserDetails user, ChildForm form) {
        Long householdId = requireParentHouseholdId(user);
        Instant now = Instant.now();
        Child child = new Child();
        child.setHouseholdId(householdId);
        applyChildForm(child, form);
        child.setCreatedAt(now);
        child.setUpdatedAt(now);
        childRepository.save(child);
    }

    @Transactional
    public void updateChild(MinsukeUserDetails user, Long childId, ChildForm form) {
        Long householdId = requireParentHouseholdId(user);
        Child child = childRepository.findByIdAndHouseholdId(childId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        applyChildForm(child, form);
        child.setUpdatedAt(Instant.now());
        childRepository.save(child);
    }

    @Transactional
    public void deleteChild(MinsukeUserDetails user, Long childId) {
        Long householdId = requireParentHouseholdId(user);
        Child child = childRepository.findByIdAndHouseholdId(childId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        childRepository.delete(child);
    }

    @Transactional
    public void deleteHousehold(MinsukeUserDetails user, Long householdId) {
        if (user.getUser().getRole() != Role.ADMIN) {
            throw new FamilyAccessDeniedException();
        }
        Household household = findHouseholdOrThrow(householdId);
        if (userRepository.existsByHouseholdId(householdId)) {
            throw new IllegalStateException("ユーザーが紐づいている家庭は削除できません");
        }
        householdRepository.delete(household);
    }

    @Transactional(readOnly = true)
    public ParentForm toParentForm(MinsukeUserDetails user, Long parentId) {
        Long householdId = requireParentHouseholdId(user);
        Parent parent = parentRepository.findByIdAndHouseholdId(parentId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        return toParentForm(parent);
    }

    @Transactional(readOnly = true)
    public ChildForm toChildForm(MinsukeUserDetails user, Long childId) {
        Long householdId = requireParentHouseholdId(user);
        Child child = childRepository.findByIdAndHouseholdId(childId, householdId)
                .orElseThrow(FamilyNotFoundException::new);
        return toChildForm(child);
    }

    @Transactional(readOnly = true)
    public HouseholdForm toHouseholdForm(MinsukeUserDetails user) {
        Long householdId = requireParentHouseholdId(user);
        Household household = findHouseholdOrThrow(householdId);
        return toHouseholdForm(household);
    }

    private Long requireParentHouseholdId(MinsukeUserDetails user) {
        if (user.getUser().getRole() != Role.PARENT) {
            throw new FamilyAccessDeniedException();
        }
        Long householdId = user.getHouseholdId();
        if (householdId == null) {
            throw new FamilyAccessDeniedException();
        }
        return householdId;
    }

    private Household findHouseholdOrThrow(Long householdId) {
        return householdRepository.findById(householdId)
                .orElseThrow(FamilyNotFoundException::new);
    }

    private HouseholdCardDTO toCard(Household household) {
        HouseholdCardDTO dto = new HouseholdCardDTO();
        dto.setId(household.getId());
        dto.setName(household.getName());
        dto.setNameKana(household.getNameKana());
        dto.setGroupName(household.getGroupName());
        dto.setParentCount(parentRepository.countByHouseholdId(household.getId()));
        dto.setChildCount(childRepository.countByHouseholdId(household.getId()));
        return dto;
    }

    private HouseholdDetailDTO toDetail(Household household) {
        HouseholdDetailDTO dto = new HouseholdDetailDTO();
        dto.setId(household.getId());
        dto.setName(household.getName());
        dto.setNameKana(household.getNameKana());
        dto.setGroupName(household.getGroupName());

        parentRepository.findByHouseholdIdOrderByIdAsc(household.getId()).forEach(parent -> {
            HouseholdDetailDTO.ParentSummaryDTO summary = new HouseholdDetailDTO.ParentSummaryDTO();
            summary.setId(parent.getId());
            summary.setName(parent.getName());
            summary.setNameKana(parent.getNameKana());
            summary.setPhone(parent.getPhone());
            dto.getParents().add(summary);
        });

        childRepository.findByHouseholdIdOrderByIdAsc(household.getId()).forEach(child -> {
            HouseholdDetailDTO.ChildSummaryDTO summary = new HouseholdDetailDTO.ChildSummaryDTO();
            summary.setId(child.getId());
            summary.setName(child.getName());
            summary.setNameKana(child.getNameKana());
            summary.setBirthDate(child.getBirthDate());
            dto.getChildren().add(summary);
        });

        return dto;
    }

    private void applyHouseholdForm(Household household, HouseholdForm form) {
        household.setName(form.getName());
        household.setNameKana(form.getNameKana());
        household.setGroupName(emptyToNull(form.getGroupName()));
    }

    private void applyParentForm(Parent parent, ParentForm form) {
        parent.setName(form.getName());
        parent.setNameKana(form.getNameKana());
        parent.setPhone(emptyToNull(form.getPhone()));
    }

    private void applyChildForm(Child child, ChildForm form) {
        child.setName(form.getName());
        child.setNameKana(form.getNameKana());
        child.setBirthDate(form.getBirthDate());
    }

    private HouseholdForm toHouseholdForm(Household household) {
        HouseholdForm form = new HouseholdForm();
        form.setName(household.getName());
        form.setNameKana(household.getNameKana());
        form.setGroupName(household.getGroupName());
        return form;
    }

    private ParentForm toParentForm(Parent parent) {
        ParentForm form = new ParentForm();
        form.setName(parent.getName());
        form.setNameKana(parent.getNameKana());
        form.setPhone(parent.getPhone());
        return form;
    }

    private ChildForm toChildForm(Child child) {
        ChildForm form = new ChildForm();
        form.setName(child.getName());
        form.setNameKana(child.getNameKana());
        form.setBirthDate(child.getBirthDate());
        return form;
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
