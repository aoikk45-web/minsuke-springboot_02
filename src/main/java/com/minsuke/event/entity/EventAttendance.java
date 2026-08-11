package com.minsuke.event.entity;

import java.time.Instant;

import com.minsuke.event.domain.AttendanceStatus;
import com.minsuke.event.domain.ParticipantType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_attendances")
@Getter
@Setter
@NoArgsConstructor
public class EventAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "participant_type", nullable = false, length = 10)
    private ParticipantType participantType;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @Column(name = "registered_by_user_id", nullable = false)
    private Long registeredByUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceStatus status;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private Instant registeredAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
