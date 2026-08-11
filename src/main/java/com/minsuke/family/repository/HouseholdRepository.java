package com.minsuke.family.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.family.entity.Household;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
}
