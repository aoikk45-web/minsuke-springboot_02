package com.minsuke.event.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateBetweenOrderByEventDateAscStartTimeAscIdAsc(
            LocalDate startInclusive, LocalDate endInclusive);

    List<Event> findByInstructorIdAndEventDateGreaterThanEqualOrderByEventDateAscStartTimeAscIdAsc(
            Long instructorId, LocalDate fromInclusive);

    List<Event> findByInstructorIdOrderByEventDateAscStartTimeAscIdAsc(Long instructorId);

    boolean existsByScheduleIdAndEventDate(Long scheduleId, LocalDate eventDate);

    List<Event> findByScheduleIdOrderByEventDateAscStartTimeAscIdAsc(Long scheduleId);
}
