package com.minsuke.instructor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.instructor.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findAllByOrderByNameKanaAscIdAsc();

    List<Instructor> findByActiveTrueOrderByNameKanaAscIdAsc();
}
