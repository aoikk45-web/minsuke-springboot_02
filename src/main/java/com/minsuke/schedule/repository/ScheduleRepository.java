package com.minsuke.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByUpdatedAtDescIdDesc();
}
