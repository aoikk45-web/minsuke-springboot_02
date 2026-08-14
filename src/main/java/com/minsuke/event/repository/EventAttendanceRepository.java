package com.minsuke.event.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minsuke.event.domain.AttendanceStatus;
import com.minsuke.event.domain.ParticipantType;
import com.minsuke.event.entity.EventAttendance;

public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {

    long countByEventIdAndStatus(Long eventId, AttendanceStatus status);

    List<EventAttendance> findByEventIdAndStatusOrderByIdAsc(Long eventId, AttendanceStatus status);

    Optional<EventAttendance> findFirstByEventIdAndParentIdOrderByIdDesc(Long eventId, Long parentId);

    Optional<EventAttendance> findFirstByEventIdAndChildIdOrderByIdDesc(Long eventId, Long childId);

    Optional<EventAttendance> findFirstByEventIdAndHouseholdIdAndParticipantTypeOrderByIdDesc(
            Long eventId, Long householdId, ParticipantType participantType);

    List<EventAttendance> findByHouseholdIdAndStatusAndEventIdIn(
            Long householdId, AttendanceStatus status, Collection<Long> eventIds);
}
