package com.minsuke.family.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.family.entity.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByHouseholdIdOrderByIdAsc(Long householdId);

    Optional<Child> findByIdAndHouseholdId(Long id, Long householdId);

    long countByHouseholdId(Long householdId);
}
