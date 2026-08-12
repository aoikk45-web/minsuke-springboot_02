package com.minsuke.instructor.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorWorkloadDTO {

    private List<AssignedEventDTO> upcomingEvents = new ArrayList<>();
    private List<MonthlyCountDTO> monthlyCounts = new ArrayList<>();
    private long totalAssignedCount;

    @Getter
    @Setter
    public static class AssignedEventDTO {
        private Long id;
        private String title;
        private LocalDate eventDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Getter
    @Setter
    public static class MonthlyCountDTO {
        private String yearMonthLabel;
        private long count;
    }
}
