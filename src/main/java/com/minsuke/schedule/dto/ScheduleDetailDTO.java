package com.minsuke.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.minsuke.schedule.domain.ScheduleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDetailDTO {

    private Long id;
    private String title;
    private String description;
    private ScheduleType scheduleType;
    private String scheduleTypeLabel;
    private List<Integer> daysOfWeek = new ArrayList<>();
    private String daysOfWeekLabel;
    private LocalDate oneOffDate;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Long instructorId;
    private String instructorName;
    private boolean active;
    private List<GeneratedEventDTO> generatedEvents = new ArrayList<>();

    @Getter
    @Setter
    public static class GeneratedEventDTO {
        private Long id;
        private LocalDate eventDate;
        private LocalTime startTime;
        private String title;
    }
}
