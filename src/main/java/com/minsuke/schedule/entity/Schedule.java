package com.minsuke.schedule.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.schedule.domain.ScheduleType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false, length = 20)
    private ScheduleType scheduleType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "schedule_weekdays", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "day_of_week", nullable = false)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private Set<Integer> daysOfWeek = new LinkedHashSet<>();

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "one_off_date")
    private LocalDate oneOffDate;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column
    private Integer capacity;

    @Column(name = "instructor_id")
    private Long instructorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_unit", length = 20)
    private ParticipationUnit participationUnit;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_by_user_id", nullable = false)
    private Long createdByUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
