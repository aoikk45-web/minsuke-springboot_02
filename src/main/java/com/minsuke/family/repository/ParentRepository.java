package com.minsuke.family.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.family.entity.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    List<Parent> findByHouseholdIdOrderByIdAsc(Long householdId);

    Optional<Parent> findByIdAndHouseholdId(Long id, Long householdId);

    long countByHouseholdId(Long householdId);
}
